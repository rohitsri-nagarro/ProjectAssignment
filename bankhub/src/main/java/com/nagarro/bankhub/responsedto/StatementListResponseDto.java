package com.nagarro.bankhub.responsedto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementListResponseDto {
	private String accountNumber;
	private List<StatementResponseDto> statememts;
	private String message;

}
