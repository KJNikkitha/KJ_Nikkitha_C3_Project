import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    List<Item> selectedItemsForCalculatingCost;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void creating_restaurant_for_testing() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime testIfRestaurantIsOpen = LocalTime.parse("11:30:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant) ;
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(testIfRestaurantIsOpen) ;
        assertTrue(spiedRestaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime testIfRestaurantIsClosed = LocalTime.parse("23:30:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant) ;
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(testIfRestaurantIsClosed) ;
        assertFalse(spiedRestaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
             assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void selected_item_from_list_should_return_order_cost_after_adding(){
        int totalCostOfSelectedItems;
        selectedItemsForCalculatingCost=restaurant.getMenu();
        totalCostOfSelectedItems = restaurant.getTotalCost(selectedItemsForCalculatingCost) ;
        assertEquals(388, totalCostOfSelectedItems);
    }

    @Test
    public void removing_selected_item_from_list_should_return_reduced_order_cost(){
        selectedItemsForCalculatingCost=restaurant.getMenu();
        selectedItemsForCalculatingCost.remove(1);
        int totalCostOfSelectedItems;
        totalCostOfSelectedItems = restaurant.getTotalCost(selectedItemsForCalculatingCost) ;
        assertEquals(119, totalCostOfSelectedItems);

    }


}