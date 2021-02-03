package com.hindustantimes.SubscriptionPortal.Validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class OrderAddressValidator implements 
  ConstraintValidator<OrderAddressConstraint, String> {
 
    @Override
    public void initialize(OrderAddressConstraint orderField) {
    }
 
  
//	public boolean isAddressCheck(String orderField, Lead lead) {
//    	//flatnumber
//		//floorno
//		//blockandstreat
//		//city
//		//state
//		//pincode
////    	if(lead.isAddressFilled()) {
////    		return orderField != null && !orderField.isEmpty();
////    	}else {
////    		return true;
////    	}
//
//
//    }

	@Override
	public boolean isValid(String orderField, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
        
		
		return orderField != null && orderField.matches("[0-9]+")
		          && (orderField.length() > 8) && (orderField.length() < 14);
	}
 
}
