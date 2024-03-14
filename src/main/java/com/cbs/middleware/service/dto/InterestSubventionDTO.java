package com.cbs.middleware.service.dto;

import java.util.Date;

public class InterestSubventionDTO {

    private String financialYear;


    //@Column(name = "pacs_number")
    private String pacsNumber;

    //  @Column(name = "from_date")
    private Date fromDate;

    //  @Column(name = "to_date")
    private Date toDate;

    /// @Column(name = "from_amount")
    private Long fromAmount;

    //@Column(name = "to_amount")
    private Long toAmount;

    // @Column(name = "from_interest")
    private Long fromInterest;

    //  @Column(name = "to_interest")
    private Long toInterest;

    //  @Column(name = "from_bank_interest")
    private Long fromBankInterest;

    // @Column(name = "to_bank_interest")
    private Long toBankInterest;

    /// @Column(name = "last_credit_date")
    private Long lastCreditDate;

    // @Column(name = "due_date")
    private Long dueDate;

    // @Column(name = "report_type")
    private Long reportType;

    ///  @Column(name = "report_condition")
    private Long reportCondition;
}
