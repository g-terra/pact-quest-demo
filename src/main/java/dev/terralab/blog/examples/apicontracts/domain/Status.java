package dev.terralab.blog.examples.apicontracts.domain;

import dev.terralab.blog.examples.apicontracts.execption.InvalidStatusException;

public enum Status {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;
    
    public static Status from(String status) {
       try {
              return Status.valueOf(status);
         } catch (IllegalArgumentException e) {
              throw new InvalidStatusException("Invalid status: " + status);
       }
    }
    
    
}
