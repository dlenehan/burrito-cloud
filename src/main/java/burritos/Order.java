package burritos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import burritos.Taco;

@Data
public class Order {
private long id;
private Date placedAt;
@NotBlank(message="name is required")	
private String name;
@NotBlank(message="city is required")
private String city;
@NotBlank(message="street is required")
private String street;
@NotBlank(message="state is required")
private String state;
@NotBlank(message="zip is required")
private String zip;
@CreditCardNumber(message="Not a valid credit card number")
private String ccNumber;
@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
message="Must be formatted MM/YY")
private String ccExpiration;
@Digits(integer=3,fraction=0,message="Invalid CVV")
private String ccCVV;
private List<Taco> tacos = new ArrayList<>();
public void addDesign(Taco design) {
    this.tacos.add(design);
  }	

}
