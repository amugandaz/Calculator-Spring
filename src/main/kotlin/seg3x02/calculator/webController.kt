package seg3x02.calculator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WebController {
    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("error", "")
        model.addAttribute("firstNumber", "")
        model.addAttribute("secondNumber", "")
        model.addAttribute("result", "")
    }

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping(value = ["/calculate"])
    fun doCalculate(
        @RequestParam(value = "firstNumber", required = false) firstNumber: String?,
        @RequestParam(value = "secondNumber", required = false) secondNumber: String?,
        @RequestParam(value = "operation", required = false) operation: String?,
        model: Model
    ): String {
        var firstNumberVal: Double? = null
        var secondNumberVal: Double? = null
        var result: String? = null 
    
        try {
            firstNumberVal = firstNumber?.toDouble()
        } catch (exp: NumberFormatException) {
            model.addAttribute("error", "firstNumberFormatError")
        }
        
        try {
            secondNumberVal = secondNumber?.toDouble()
        } catch (exp: NumberFormatException) {
            model.addAttribute("error", "secondNumberFormatError")
        }        
    
        if (firstNumberVal != null && secondNumberVal != null) {
            when (operation) {
                "somme" -> result = (firstNumberVal + secondNumberVal).toString()
                "difference" -> result = (firstNumberVal - secondNumberVal).toString()
                "multiplication" -> result = (firstNumberVal * secondNumberVal).toString()
                "division" -> {
                    if (secondNumberVal != 0.0) {
                        result = (firstNumberVal / secondNumberVal).toString()
                    } else {
                        model.addAttribute("error", "DivisionByZeroError")
                    }
                }
                else -> model.addAttribute("error", "OperationFormatError")
            }
        }
    
        model.addAttribute("firstNumber", firstNumber ?: "")
        model.addAttribute("secondNumber", secondNumber ?: "")
        model.addAttribute("result", result ?: "") 
    
        return "home"
    }    
}