package com.investocks.controllers;

import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.investocks.entities.Ask;
import com.investocks.entities.Bid;
import com.investocks.entities.Company;
import com.investocks.entities.ShareBalance;
import com.investocks.entities.Trade;
import com.investocks.entities.User;
import com.investocks.forms.OrderForm;
import com.investocks.forms.PendingOrders;
import com.investocks.forms.UserPortfolio;
import com.investocks.helper.Helper;
import com.investocks.helper.Message;
import com.investocks.helper.MessageType;
import com.investocks.services.AskServices;
import com.investocks.services.BidServices;
import com.investocks.services.CompanyServices;
import com.investocks.services.ShareBalanceServices;
import com.investocks.services.TradeServices;
import com.investocks.services.UserServices;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private CompanyServices companyServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private ShareBalanceServices shareBalanceServices;
    @Autowired
    private BidServices bidServices;
    @Autowired
    private AskServices askServices;
    @Autowired
    private TradeServices tradeServices;

    @RequestMapping("/welcome")
    public String welcomeController(Model model, Authentication authentication, HttpSession session) {

        String userName = Helper.getEmailOfLoggedInUser(authentication);
        session.setAttribute("loggedInUser", userName);
        System.out.println("user loggin with : "+userName);

        List<Company> companies = companyServices.getCompanyList();
        model.addAttribute("companies", companies);

        return "welcome";
    }
    @GetMapping("/order")
    public String orderController(Model model) {
        model.addAttribute("company", "");
        model.addAttribute("action", "");
        return "order";
    }
    
    @GetMapping("/order/buy")
    public String buyController(@RequestParam String company,Model model) {
        model.addAttribute("company", company);
        model.addAttribute("action", "buy");
        return "order";
    }
    @GetMapping("/order/sell")
    public String sellController(@RequestParam String company,Model model) {
        model.addAttribute("company", company);
        model.addAttribute("action", "sell");
        return "order";
    }
    @PostMapping("/placeOrder")
    public String postMethodName(@ModelAttribute OrderForm orderForm, HttpSession session) {

        String userEmail = (String)session.getAttribute("loggedInUser");
        Optional<User> optionalUser = userServices.findByEmail(userEmail);
        User user = optionalUser.get();

        Optional<Company> company = companyServices.getCompanyByName(orderForm.getCompanyName());
        if(!company.isPresent()){
            Message message = Message.builder().content("Company not found, enter a valid company.").type(MessageType.red).build();
            session.setAttribute("message", message);
            return "order";
        }
        float upperLimit = company.get().getClosedPrice() * (1 + (company.get().getCircuitLimit()/100));
        float lowerLimit = company.get().getClosedPrice() * (1 - (company.get().getCircuitLimit()/100));
        if(orderForm.getPrice() > upperLimit || orderForm.getPrice() < lowerLimit){
            Message message = Message.builder().content("Circuit limit crossed, enter a valid price.").type(MessageType.red).build();
            session.setAttribute("message", message);
            return "order";
        }
        // validate userbalance
        if("Buy".equals(orderForm.getAction())){
            int totalOrderAmount = orderForm.getPrice() * orderForm.getQuantity();
            if(user.getAccountBalance() < totalOrderAmount){
                Message message = Message.builder().content("Insufficient user balance.").type(MessageType.red).build();
                session.setAttribute("message", message);
                return "order";
            }
        }
        else{
            Optional<ShareBalance> optionalShareBalance = shareBalanceServices.getBalanceOfUserForCompany(user, company.get());
            ShareBalance shareBalance;
            if(optionalShareBalance.isPresent()) shareBalance = optionalShareBalance.get();
            else shareBalance = null;
            if(shareBalance==null || shareBalance.getQuantity() < orderForm.getQuantity()){
                Message message = Message.builder().content("Insufficient share balance.").type(MessageType.red).build();
                session.setAttribute("message", message);
                return "order";
            }
        }
        // place order
        if("Buy".equals(orderForm.getAction())){
            Bid bid = new Bid(company.get(), orderForm.getPrice(), orderForm.getQuantity(), user);
            bidServices.placeBid(bid);
        }
        else{
            Ask ask = new Ask(company.get(), orderForm.getPrice(), orderForm.getQuantity(), user);
            askServices.placeAsk(ask);
        }
        // order success or failure
        this.checkTrade(company.get());
        
        Message message = Message.builder().content("Order placed successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        
        return "order";
    }
    @GetMapping("/portfolio")
    public String getMethodName(HttpSession session, Model model) {
        String userEmail = (String)session.getAttribute("loggedInUser");
        System.out.println(userEmail);
        
        Optional<User> optionalUser = userServices.findByEmail(userEmail);
        model.addAttribute("userDetails", optionalUser.get());
        
        List<ShareBalance> shares = shareBalanceServices.getBalanceOfUser(optionalUser.get());
        List<UserPortfolio> userPortfolioEntries = new ArrayList<>(shares.size());
        
        for(int i=0;i<shares.size();i++){
            String companyName = shares.get(i).getCompany().getName();
            float companyCurrentPrice = shares.get(i).getCompany().getCurrentPrice();
            float totalAmountSpent = shares.get(i).getAmountSpent();
            int shareCount = shares.get(i).getQuantity();
            float currentProfit = ((companyCurrentPrice*shareCount) - totalAmountSpent) / totalAmountSpent;
            
            UserPortfolio userPortfolio = new UserPortfolio(companyName, shareCount, companyCurrentPrice, currentProfit);
            userPortfolioEntries.add(userPortfolio);
        }
        model.addAttribute("userPortfolioEntries", userPortfolioEntries);
        
        model.addAttribute("loggedUser", userEmail);
        return "portfolio";
    }
    
    @GetMapping("/pending-orders")
    public String pendingOrder(Model model, HttpSession session) {
        
        String userEmail = (String)session.getAttribute("loggedInUser");
        Optional<User> optionalUser = userServices.findByEmail(userEmail);
        List<Bid> pendingBids = bidServices.pendingBids(optionalUser.get().getId());
        List<PendingOrders> pendingOrders = new ArrayList<>();
        for(int i=0;i<pendingBids.size();i++){
            PendingOrders pending = new PendingOrders(pendingBids.get(i).getCompany().getName(),"Buy",pendingBids.get(i).getPrice(),pendingBids.get(i).getRemainingQty(),pendingBids.get(i).getPlacedTime());
            pendingOrders.add(pending);
        }
        List<Ask> pendingAsks = askServices.pendingAsks(optionalUser.get().getId());
        for(int i=0;i<pendingAsks.size();i++){
            PendingOrders pending = new PendingOrders(pendingAsks.get(i).getCompany().getName(),"Sell",pendingAsks.get(i).getPrice(),pendingAsks.get(i).getRemainingQty(),pendingAsks.get(i).getPlacedTime());
            pendingOrders.add(pending);
        }
        
        model.addAttribute("pendingOrders", pendingOrders);
        return "pendingOrders";
    }
    

    public void checkTrade(Company company){
        int i=0;//to prevent infinite loop
        while(i<50) { 
            //get bid and ask tables sorted
            List<Bid> bidTable = bidServices.bidSequenceForCompany(company.getId());
            List<Ask> askTable = askServices.askSequenceForCompany(company.getId());
            System.out.println(bidTable);
            System.out.println(askTable);
            //match for trade
            if(bidTable.isEmpty() || askTable.isEmpty()) break;
            else if(bidTable.get(0).getPrice() >= askTable.get(0).getPrice()){
                this.trade(bidTable.get(0), askTable.get(0), company);
            }
            else break;
            //update bid , ask, trade, sharebalance, company tables
            //put this in a loop
            i++;
        }

    }
    public void trade(Bid bid, Ask ask, Company company){
        int tradePrice = ask.getPrice();
        int tradeQuantity = min(bid.getQuantity(), ask.getQuantity());
        int tradeAmount = tradePrice * tradeQuantity;

        Trade tradeChanges = new Trade(ask, bid, company, tradePrice, tradeQuantity);
        tradeServices.completeTrade(tradeChanges);

        Bid bidChanges = new Bid(company, bid.getPlacedTime(), bid.getPrice(), bid.getQuantity(), (bid.getQuantity()-tradeQuantity), bid.getUser());
        bidServices.updateBid(bidChanges);

        Ask askChanges = new Ask(company, ask.getPlacedTime(), ask.getPrice(), ask.getQuantity(), (ask.getQuantity()-tradeQuantity), ask.getUser());
        askServices.updateAsk(askChanges);

        if(!shareBalanceServices.getBalanceOfUserForCompany(bid.getUser(), company).isPresent()){
            ShareBalance newShareBalance = new ShareBalance(tradeQuantity,tradeAmount, company, bid.getUser());
            shareBalanceServices.newBalanceEntry(newShareBalance);
        }
        else shareBalanceServices.addBalance(bid.getUser(), company, tradeQuantity, tradeAmount);
        shareBalanceServices.subtractBalance(ask.getUser(), company, tradeQuantity, tradeAmount);

        userServices.updateUserBalance(bid.getUser().getId(), (-1)*tradeAmount);
        userServices.updateUserBalance(ask.getUser().getId(), tradeAmount);

        companyServices.updateCompanyOnTrade(company, tradePrice);

        System.out.println("done");

    }
    public int evaluateTradePrice(int bidPrice, int askPrice){
        return 0;
    }
    
}
