package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CustomerForm extends FormLayout {

    private CustomerService customerService = CustomerService.getInstance();
    private MainView mainView;
    private Customer customer;
    private Binder<Customer> customerBinder = new Binder<>(Customer.class);

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    public CustomerForm(MainView mainView) {
        this.mainView = mainView;
        status.setItems(CustomerStatus.values());
        customerBinder.bindInstanceFields(this);
        save.getElement().setAttribute("theme", "primary");

        save.addClickListener(event -> this.save());
        delete.addClickListener(event -> this.delete());

        HorizontalLayout buttonsBlock = new HorizontalLayout(save, delete);
        setCustomer(null);

        add(firstName, lastName, status, buttonsBlock);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customerBinder.setBean(customer);
        boolean enabled = customer != null;
        save.setEnabled(enabled);
        delete.setEnabled(enabled);
        if(enabled){
            firstName.focus();
        }

    }

    private void delete(){
        customerService.delete(customer);
        mainView.updateList();
        setCustomer(null);
    }

    private void save(){
        customerService.save(customer);
        mainView.updateList();
        setCustomer(null);
    }

}
