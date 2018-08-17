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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import burritos.Ingredient;
import burritos.Ingredient.Type;
import burritos.Order;
import burritos.Taco;
import burritos.data.IngredientRepository;
import burritos.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")
public class DesignTacoController {
	
private final IngredientRepository ingredientRepo;
private TacoRepository designRepo;

@Autowired
public DesignTacoController(IngredientRepository ingredientRepo,
		TacoRepository designRepo) {
	this.ingredientRepo = ingredientRepo;
	this.designRepo = designRepo;
}
	
@ModelAttribute(name="order")
public Order order() {
	return new Order();
}

@ModelAttribute(name="taco")
public Taco taco() {
	return new Taco();
}


@GetMapping
public String showDesignForm(Model model) {

List<Ingredient> ingredients = new ArrayList<>();
ingredientRepo.findAll().forEach(i-> ingredients.add(i));
Type[] types = Ingredient.Type.values();
for (Type type: types) {
	log.info("here are ingredients: " + ingredients);
	model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
	}


model.addAttribute("design", new Taco());
log.info("here is design: " + "design");
return "design";

}


private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
	
	return ingredients.stream()
			.filter(x -> x.getType().equals(type))
			.collect(Collectors.toList());
	
}


@PostMapping
public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
  if (errors.hasErrors()) {
	  return "design";
  }
	
	log.info("Processing design: " + design);
Taco saved = designRepo.save(design);
log.info("SAVED IS : " + saved);
order.addDesign(saved);
  
  return "redirect:/orders/current";
}
      
    

     
}
