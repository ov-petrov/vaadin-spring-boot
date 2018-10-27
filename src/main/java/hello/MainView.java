package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    MainController mainController;

    @Autowired
    CustomerRepository customerRepository;

    CustomerService customerService = CustomerService.getInstance();

    private Input nameInput = new Input();
    private Input emailInput = new Input();

    private Grid<Customer> usersGrid = new Grid<>();

    public MainView() {
        usersGrid.setSizeFull();
        usersGrid.addColumn(Customer::getFirstName).setHeader("First name");
        usersGrid.addColumn(Customer::getLastName).setHeader("Last name");
        usersGrid.addColumn(Customer::getStatus).setHeader("Status");
        usersGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        Button register = new Button("Наполнить грид",
                e -> updateList());

        Button saveButton = new Button("Сохранить выбранных пользователей",
                e -> usersGrid.getSelectedItems().forEach(c -> customerRepository.save(c)));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(register, saveButton);
        add(usersGrid, horizontalLayout);
        setHeight("100vh");
    }

    public void updateList() {
        usersGrid.setItems(customerService.findAll());
    }

}
