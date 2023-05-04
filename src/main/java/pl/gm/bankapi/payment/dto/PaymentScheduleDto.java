package pl.gm.bankapi.payment.dto;

import lombok.Data;
import pl.gm.bankapi.loan.dto.LoanDto;

import java.util.List;

@Data
public class PaymentScheduleDto {

    private Long id;
    private LoanDto loan;
    private List<PaymentDto> payments;

}
