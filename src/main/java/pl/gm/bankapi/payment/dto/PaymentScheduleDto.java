package pl.gm.bankapi.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentScheduleDto {

    private Long id;
    private List<PaymentDto> payments;
}
