package com.bazzar.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresstypelookup")
public class AddressTypeLookup extends AbstractLookupType  {

	private static final long serialVersionUID = 3212625355790851086L;
	
	public static final AddressTypeLookup B = createConstant("B", "Billing", AddressTypeLookup.class);
	public static final AddressTypeLookup H = createConstant("H", "Home", AddressTypeLookup.class);
	public static final AddressTypeLookup M = createConstant("M", "Mail", AddressTypeLookup.class);
	public static final AddressTypeLookup R = createConstant("R", "Residence", AddressTypeLookup.class);
	public static final AddressTypeLookup S = createConstant("S", "Shipping", AddressTypeLookup.class);
	
 	@Id
    @Column(name="Code")
    @Override
    public String getCode() {
        return super.getCode();
    }

    @Column(name="Description")
    @Override
    public String getDescription() {
        return super.getDescription();
    }

}
