package burritos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import burritos.Ingredient;
import burritos.Ingredient.Type;
import burritos.Taco;
import burritos.data.IngredientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")
public class DesignTacoController {
	
private final IngredientRepository ingredientRepo;

@Autowired
public DesignTacoController(IngredientRepository ingredientRepo) {
	this.ingredientRepo = ingredientRepo;
}
	
@GetMapping
public String showDesignForm(Model model) {

List<Ingredient> ingredients = new ArrayList<>();
ingredientRepo.findAll().forEach(i-> ingredients.add(i));
Type[] types = Ingredient.Type.values();
for (Type type: types) {
	model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
	}


model.addAttribute("design", new Taco());

return "design";

}


private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
	
	return ingredients.stream()
			.filter(x -> x.getType().equals(type))
			.collect(Collectors.toList());
	
}


@PostMapping
public String processDesign(@Valid Taco design, Errors errors) {
  // Save the taco design...
  // We'll do this in chapter 3
  if (errors.hasErrors()) {
	  return "design";
  }
	
	log.info("Processing design: " + design);

  
  return "redirect:/orders/current";
}
      
    

     
}
