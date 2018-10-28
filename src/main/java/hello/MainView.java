package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    MainController mainController;

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerService customerService = CustomerService.getInstance();

    private TextField filterText = new TextField();
    private TextField nameInput = new TextField();
    private TextField emailInput = new TextField();

    private Button clearFilterBtn = new Button();
    private Button addCustomerBtn = new Button("Add customer");

    private CustomerForm form = new CustomerForm(this);

    private Grid<Customer> usersGrid = new Grid<>();

    public MainView() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        clearFilterBtn.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
        clearFilterBtn.addClickListener(event -> filterText.clear());

        addCustomerBtn.addClickListener(event -> {
           usersGrid.asSingleSelect().clear();
           form.setCustomer(new Customer());
        });

        usersGrid.setSizeFull();
        usersGrid.addColumn(Customer::getFirstName).setHeader("First name");
        usersGrid.addColumn(Customer::getLastName).setHeader("Last name");
        usersGrid.addColumn(Customer::getStatus).setHeader("Status");
        usersGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Button register = new Button("Наполнить грид",
                e -> updateList());

        Button saveButton = new Button("Сохранить выбранных пользователей",
                e -> usersGrid.getSelectedItems().forEach(c -> customerRepository.save(c)));

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.add(filterText, clearFilterBtn, addCustomerBtn);
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.add(register, saveButton);
        HorizontalLayout main = new HorizontalLayout(usersGrid, form);
        main.setAlignItems(Alignment.START);
        main.setSizeFull();

        add(topLayout, main, bottomLayout);
        setHeight("100vh");

        usersGrid.asSingleSelect().addValueChangeListener(event -> form.setCustomer(event.getValue()));
    }

    public void updateList() {
        usersGrid.setItems(customerService.findAll(filterText.getValue()));
    }

}
