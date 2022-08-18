package edu.epam.taskxml.builder;

import edu.epam.taskxml.entity.AbstractVoucher;
import edu.epam.taskxml.exeption.VoucherException;


import java.util.HashSet;
import java.util.Set;

public abstract class AbstractVoucherBuilder {
    protected Set<AbstractVoucher> vouchers;

    protected AbstractVoucherBuilder() {
        vouchers = new HashSet<>();
    }

    public Set<AbstractVoucher> getVouchers() {
        return vouchers;
    }

    public abstract void buildVouchers(String path) throws VoucherException;

}
