package com.wilterson.cms.common.validation.semantic;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO:
//  1)
//  The goal is to accumulate the issues in a collection, rather than throwing exceptions when an issue
//  is found. This is to gather all issues in a collection and return all of them to the user, in one go.
//  The approach implemented so far works, but it has this drawback of having to pass the collection of
//  SemanticValidationException to the SemanticValidator::validate to store the issues. The approach that I'm
//  more inclined to, which is not implemented yet, is to change the scope of the SemanticValidatorFactory
//  bean to "request" and add a field "Collection<SemanticValidationException> issues". This won't cause any
//  concurrency issues because the bean has a "request" scope, which means that Spring will hand a new instance
//  over to each new thread. Each request will have it's own copy of the issues.
//  ---
//  2)
//  How to chain children semantic validations? The validations work for merchants but not for
//  children, i.e. Location. The semantic validation logic must chain the children so that, given
//  a merchant, the merchant entity and all the children are validated and the issues compiled in
//  the same collection. Also, the semantic validation logic must be able to validate a merchant's
//  child entity directly. There are times where the merchant entity is not available, only the
//  children.

@Component
public final class SemanticValidatorFactory {

    private SemanticValidator<Merchant> uniqueName;
    private SemanticValidator<Merchant> uniqueGuid;
    private SemanticValidator<Merchant> defaultLocation;
    private SemanticValidator<Merchant> locationNotAllowed;
    private SemanticValidator<Location> uniqueLocation; //TODO: define how to use it

    @Autowired
    public void setUniqueName(UniqueNameValidation uniqueNameValidation) {
        this.uniqueName = uniqueNameValidation;
    }

    @Autowired
    public void setUniqueGuid(UniqueGuidValidation uniqueGuidValidation) {
        this.uniqueGuid = uniqueGuidValidation;
    }

    @Autowired
    public void setDefaultLocation(DefaultLocationValidation defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    @Autowired
    public void setLocationNotAllowed(LocationNotAllowedValidation locationNotAllowed) {
        this.locationNotAllowed = locationNotAllowed;
    }

    @Autowired
    public void setUniqueLocation(UniqueLocationValidation uniqueLocation) {
        this.uniqueLocation = uniqueLocation;
    }

    public SemanticValidator<Merchant> subMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(defaultLocation);
    }

    public SemanticValidator<Merchant> multiMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    public SemanticValidator<Merchant> partnerSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }
}
