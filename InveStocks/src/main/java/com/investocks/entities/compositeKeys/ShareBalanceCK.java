package com.investocks.entities.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

import com.investocks.entities.Company;
import com.investocks.entities.User;

public class ShareBalanceCK implements Serializable{
    
    private Company company;
    private User user;

    public ShareBalanceCK() {
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        else if(!(obj instanceof ShareBalanceCK)) return false;
        ShareBalanceCK shareBalanceCK = (ShareBalanceCK) obj;
        return this.company.equals(shareBalanceCK.company) && this.user.equals(shareBalanceCK.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.company,this.user);
    }
    
}
