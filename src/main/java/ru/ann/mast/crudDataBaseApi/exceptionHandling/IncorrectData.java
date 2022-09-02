package ru.ann.mast.crudDataBaseApi.exceptionHandling;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncorrectData {
	
	private String info;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String debugMessage;
	
   @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;
    
   public IncorrectData (String info, String debugMessage) {
	   this.info = info;
   }
}
