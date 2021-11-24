import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class showMinistry extends Application {
    private Stage window;
    private Button btShowMOHScreen;
    private Button[] navButtons;
    private StackPane ctnContent;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setScene(new CustomScene(new MainMenuLayout()));
        window.setTitle("Covid 19 Program");
        window.setMaxWidth(1000);
        window.setMinWidth(1000);
        window.setMaxHeight(600);
        window.setMinHeight(600);
        window.resizableProperty().setValue(false);
        window.show();
        
    }

    static class CustomScene extends Scene {
        public CustomScene(Parent parent) {
            super(parent);
            getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }

        public CustomScene(Parent parent, double v, double v1) {
            super(parent, v, v1);
            getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }
    }

    static class AlertBox {
        public static void display(String title, String message) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setMinWidth(250);
    
            StackPane layout = new StackPane();
            layout.setPadding(new Insets(50));
            layout.getChildren().add(new Label(message));
            layout.setAlignment(Pos.CENTER);
    
            stage.setScene(new CustomScene(layout));
            stage.showAndWait();
        }
    
        public static void displayFromLayout(String title, Pane layout) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new CustomScene(layout, 400, 660));
            stage.setResizable(false);
            stage.showAndWait();
        }
    }

   
    class MainMenuLayout extends GridPane implements EventHandler<ActionEvent> {
        public MainMenuLayout() {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(25);
            getColumnConstraints().add(columnConstraints);

            ColumnConstraints columnConstraints2 = new ColumnConstraints();
            columnConstraints2.setPercentWidth(75);
            getColumnConstraints().add(columnConstraints2);

            VBox ctnNav = new VBox();
            GridPane.setConstraints(ctnNav, 0, 0);
            ctnNav.setId("nav-pane");
            ctnNav.prefHeightProperty().bind(heightProperty());
            ctnNav.setStyle("-fx-background-color: #000202");
            
            ImageView imageView = new ImageView( "https://tse3.mm.bing.net/th?id=OIP.sLgnKMstbVjTPj7D4iGFWAHaH7&pid=Api&P=0&w=300&h=300");
            ctnNav.getChildren().add(imageView);
            StackPane ctnTitle = new StackPane();
            ctnTitle.setAlignment(Pos.CENTER);
            ctnTitle.setId("role-pane");
            Label lbTitle = new Label("Covid 19 Program");
            lbTitle.setFont(Font.font("Time New Roman",FontWeight.BOLD,28));
            lbTitle.setPadding(new Insets(0, 0, 50, 0));
            lbTitle.getStyleClass().add("menu-head-label");
            ctnTitle.getChildren().add(lbTitle);

            btShowMOHScreen = new Button("Ministry of Health");
            btShowMOHScreen.getStyleClass().add("nav-button");
            btShowMOHScreen.prefWidthProperty().bind(ctnNav.widthProperty());
            btShowMOHScreen.setOnAction(this);
            
           
            
            navButtons = new Button[]{ btShowMOHScreen};
            
            ctnNav.getChildren().addAll(ctnTitle, btShowMOHScreen);

            ctnContent = new StackPane();
            ctnContent.setAlignment(Pos.CENTER);
            GridPane.setConstraints(ctnContent, 1, 0);


            getChildren().addAll(ctnNav, ctnContent);
        }

        @Override
        public void handle(ActionEvent event) {
            ctnContent.getChildren().clear();
            
           if (event.getSource() == btShowMOHScreen) {
                changeTab(0);
                try {
                    window.setScene(new CustomScene(new MinistryOfHealthLayout(new MinistryOfHealth()), 1000, 600));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void changeTab(int tabIndex) {
            for (Button navButton : navButtons) {
                navButton.getStyleClass().remove("nav-button-active");
            }
            navButtons[tabIndex].getStyleClass().add("nav-button-active");
        }
    }


    class RecipientDetailLayout extends GridPane {
        private String[] vaccinationStatusDescription = {"", "Pending", "1st Dose Appointment", "1st Dose Completed", "2nd Dose Appointment", "2nd Dose Completed"};

        RecipientDetailLayout(Recipient recipient) {
            setVgap(10);
            setAlignment(Pos.CENTER);
            setPadding(new Insets(0, 0, 0, 20));

            Label lbFullName = new Label("Name");
            lbFullName.getStyleClass().add("text-title");
            GridPane.setConstraints(lbFullName, 0, 0);
            Label lbFullName2 = new Label(recipient.getName());
            GridPane.setConstraints(lbFullName2, 0, 1);
            Label lbEmpty = new Label("");
            GridPane.setConstraints(lbEmpty, 0, 2);

            Label lbPhoneNo = new Label("Phone Number");
            lbPhoneNo.getStyleClass().add("text-title");
            GridPane.setConstraints(lbPhoneNo, 0, 3);
            Label lbPhoneNo2 = new Label(recipient.getPhoneNumber());
            GridPane.setConstraints(lbPhoneNo2, 0, 4);
            Label lbEmpty2 = new Label("");
            GridPane.setConstraints(lbEmpty2, 0, 5);

            Label lbStatus = new Label("Status");
            lbStatus.getStyleClass().add("text-title");
            GridPane.setConstraints(lbStatus, 0, 6);
            Label lbStatus2 = new Label(vaccinationStatusDescription[recipient.getVaccinationStatus()]);
            GridPane.setConstraints(lbStatus2, 0, 7);
            Label lbEmpty3 = new Label("");
            GridPane.setConstraints(lbEmpty3, 0, 8);

            Label lbAppointment = new Label("Appoinments");
            lbAppointment.getStyleClass().add("text-title");
            GridPane.setConstraints(lbAppointment, 0, 9);
            Label lbDose1 = new Label();
            String messageDose1 = "Dose 1 is not appointed";
            if (recipient.getAppointments() != null && recipient.getAppointments().size() > 0) {
                if (recipient.getAppointments().get(0).getVaccinationCenter() != null
                        && recipient.getAppointments().get(0).getAppointmentDT() != null) {
                    messageDose1 = "Dose 1: " + recipient.getAppointments().get(0).getVaccinationCenter() + ", " + recipient.getAppointments().get(0).getAppointmentDT().toString().replace('T', ' ');
                }
            }
            lbDose1.setText(messageDose1);

            GridPane.setConstraints(lbDose1, 0, 10);
            Label lbDose2 = new Label();
            String messageDose2 = "Dose 2 is not appointed";
            if (recipient.getAppointments() != null && recipient.getAppointments().size() > 1) {
                if (recipient.getAppointments().get(1).getVaccinationCenter() != null
                        && recipient.getAppointments().get(1).getAppointmentDT() != null) {
                    messageDose2 = "Dose 1: " + recipient.getAppointments().get(1).getVaccinationCenter() + ", " + recipient.getAppointments().get(1).getAppointmentDT().toString().replace('T', ' ');
                }
            }
            lbDose2.setText(messageDose2);
            GridPane.setConstraints(lbDose2, 0, 11);

            Label lbEmpty4 = new Label("");
            GridPane.setConstraints(lbEmpty4, 0, 12);

            getChildren().addAll(lbFullName, lbFullName2, lbEmpty, lbPhoneNo, lbPhoneNo2, lbEmpty2, lbStatus, lbStatus2, lbEmpty3, lbAppointment, lbDose1, lbDose2, lbEmpty4);
        }
    }

    class MinistryOfHealthLayout extends GridPane implements EventHandler<ActionEvent> {
        private MinistryOfHealth ministryOfHealth;
        private Button btDashboard, btRecipients, btVaccinationCenters, btLogOut;
        private Button[] navButtons;
        private StackPane ctnSubContent;

        MinistryOfHealthLayout(MinistryOfHealth ministryOfHealth) {
            this.ministryOfHealth = ministryOfHealth;

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(20);
            getColumnConstraints().add(columnConstraints);

            ColumnConstraints columnConstraints2 = new ColumnConstraints();
            columnConstraints2.setPercentWidth(80);
            getColumnConstraints().add(columnConstraints2);

            VBox ctnSubNav = new VBox();
            ctnSubNav.prefWidthProperty().bind(widthProperty());
            ctnSubNav.prefHeightProperty().bind(heightProperty());
            ctnSubNav.setId("nav-pane");
            GridPane.setConstraints(ctnSubNav, 0, 0);

            StackPane ctnRole = new StackPane();
            ctnRole.setAlignment(Pos.CENTER);
            ctnRole.setId("role-pane");
            Label lbRole = new Label("Ministry of Health");
            lbRole.setPadding(new Insets(0, 0, 50, 0));
            lbRole.getStyleClass().add("menu-head-label");
            ctnRole.getChildren().add(lbRole);

            btDashboard = new Button("Dashboard");
            btDashboard.getStyleClass().addAll("nav-button", "nav-button-active");
            btDashboard.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btDashboard.setOnAction(this);

            btRecipients = new Button("Recipients");
            btRecipients.getStyleClass().add("nav-button");
            btRecipients.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btRecipients.setOnAction(this);

            btVaccinationCenters = new Button("Vaccination Centers");
            btVaccinationCenters.getStyleClass().add("nav-button");
            btVaccinationCenters.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btVaccinationCenters.setOnAction(this);

            btLogOut = new Button("Log Out");
            btLogOut.getStyleClass().add("nav-button");
            btLogOut.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btLogOut.setOnAction(this);

            navButtons = new Button[]{btDashboard, btRecipients, btVaccinationCenters, btLogOut};

            ctnSubNav.getChildren().addAll(ctnRole, btDashboard, btRecipients, btVaccinationCenters, btLogOut);

            ctnSubContent = new StackPane();
            ctnSubContent.setAlignment(Pos.CENTER);
            setColumnIndex(ctnSubContent, 1);
            //GridPane.setConstraints(ctnSubContent, 1, 0);

            ctnSubContent.getChildren().add(new DashboardLayout(ministryOfHealth.getRecipients()));
            getChildren().addAll(ctnSubNav, ctnSubContent);
        }

        @Override
        public void handle(ActionEvent event) {
            ctnSubContent.getChildren().clear();
            if (event.getSource() == btDashboard) {
                changeTab(0);
                ctnSubContent.getChildren().add(new DashboardLayout(ministryOfHealth.getRecipients()));
            } else if (event.getSource() == btRecipients) {
                changeTab(1);
                ctnSubContent.getChildren().add(new RecipientTableLayout(ministryOfHealth.getRecipients()));
            } else if (event.getSource() == btVaccinationCenters) {
                changeTab(2);
                ctnSubContent.getChildren().add(new VCTableLayout(ministryOfHealth.getVaccinationCenters()));
            } else if (event.getSource() == btLogOut) {
                changeTab(3);
                window.setScene(new CustomScene(new MainMenuLayout(), 1000, 600));
            }
        }

        private void changeTab(int tabIndex) {
            for (Button navButton : navButtons) {
                navButton.getStyleClass().remove("nav-button-active");
            }
            navButtons[tabIndex].getStyleClass().add("nav-button-active");
        }

        class DashboardLayout extends GridPane {
            ArrayList<Recipient> recipients;

            public DashboardLayout(ArrayList<Recipient> recipients) {
                this.recipients = recipients;

                setPadding(new Insets(50, 0, 0, 75));
                setVgap(80);
                setHgap(40);

                String percentage = String.format("%.2f%%\nfully vaccined", getPercentageOverTotalRecipients(getTotalFullyVaccinedRecipient()));
                Button bt1 = new Button(percentage);
                bt1.setStyle("-fx-background-color:#fc7a6a");
                bt1.getStyleClass().add("dashboard-button");
                setConstraints(bt1, 0, 0);

                percentage = String.format("%.2f%%\nunvaccined", getPercentageOverTotalRecipients(getTotalUnvaccinedRecipient()));
                Button bt2 = new Button(percentage);
                bt2.setStyle("-fx-background-color: #fe276a");
                bt2.getStyleClass().add("dashboard-button");
                setConstraints(bt2, 1, 0);

                percentage = String.format("%.2f%%\nreceived one dose only", getPercentageOverTotalRecipients(getTotalRecipientWithOneDose()));
                Button bt3 = new Button(percentage);
                bt3.setStyle("-fx-background-color: #1abe6a");
                bt3.getStyleClass().add("dashboard-button");
                setConstraints(bt3, 0, 1);

               //Button bt4 = new Button(ministryOfHealth.getRecipients().size() + "\noperating VC");
                Button bt4 = new Button(getTotalDoseGivenToday() + "\ndoses given today");
                bt4.setStyle("-fx-background-color: #bb4ce1");
                bt4.getStyleClass().add("dashboard-button");
                setConstraints(bt4, 1, 1);
                getChildren().addAll(bt1, bt2, bt3, bt4);
            }


            // 2 not set 
            // 3 1st dose done
            //4 1dt dose done , 2nd pending
            //5 all done 

            private long getTotalUnvaccinedRecipient() {
                long count = 0;
                for (Recipient recipient : recipients) {
                    if (recipient.getVaccinationStatus() == 1 || recipient.getVaccinationStatus() == 2)
                        count += 1;
                }
                return count;
            }

            private long getTotalRecipientWithOneDose() { // get number of people with 1 dose only
                long count = 0;
                for (Recipient recipient : recipients) {
                    if (recipient.getVaccinationStatus() == 3)
                        count += 1;
                }
                return count;
            }

            private long getTotalFullyVaccinedRecipient() {
                long count = 0;
                for (Recipient recipient : recipients) {
                    if (recipient.getVaccinationStatus() == 5)
                        count += 1;
                }
                return count;
            }

            private long getTotalDoseGivenToday() {
                long count = 0;
                LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                for (Recipient recipient : recipients) {
                    if ((recipient.getVaccinationStatus() == 5 && recipient.getAppointments().get(1).getAppointmentDT()
                            .truncatedTo(ChronoUnit.DAYS).isEqual(today))
                            || (recipient.getVaccinationStatus() == 3 && recipient.getAppointments().get(0).getAppointmentDT()
                            .truncatedTo(ChronoUnit.DAYS).isEqual(today)))
                        count++;
                }
                return count;
            }

            private double getPercentageOverTotalRecipients(long totalFilteredRecipients) {
                long totalRecipients = recipients.size();
                return (1.0 * totalFilteredRecipients / totalRecipients) * 100;
            }

        }

        class RecipientTableLayout extends VBox {
            public RecipientTableLayout(ArrayList<Recipient> recipients) {
                setAlignment(Pos.CENTER);
                setPadding(new Insets(20));

                Label label = new Label();
                label.setPadding(new Insets(20));
                

                HBox hBox = new HBox();
                hBox.setPadding(new Insets(10, 10, 10, 10));
                hBox.setSpacing(10);
              

                TableColumn<Recipient, String> tblColName = new TableColumn<>("Name");
                tblColName.setMinWidth(100);
                tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Recipient, String> tblColPhoneNo = new TableColumn<>("Phone No");
                tblColPhoneNo.setMinWidth(100);
                tblColPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

                TableColumn<Recipient, Integer> tblColVCStatus = new TableColumn<>("Status");
                tblColVCStatus.setMinWidth(100);
                tblColVCStatus.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

                TableColumn<Recipient, String> tblColDose1VC = new TableColumn<>("Location");
                tblColDose1VC.setCellValueFactory(cellData -> {
                    String vc = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 0) {
                        vc = cellData.getValue().getAppointments().get(0).getVaccinationCenter();
                    }
                    return new SimpleStringProperty(vc);
                });

                TableColumn<Recipient, String> tblColDose1DT = new TableColumn<>("Time");
                tblColDose1DT.setCellValueFactory(cellData -> {
                    String dt = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 0) {
                        if (cellData.getValue().getAppointments().get(0).getAppointmentDT() != null) {

                            dt = cellData.getValue().getAppointments().get(0).getAppointmentDT().toString().replace('T', ' ');
                        }
                    }
                    return new SimpleStringProperty(dt);
                });

                TableColumn<Recipient, Appointment> tblColDose1 = new TableColumn<>("Dose 1");
                tblColDose1.getColumns().addAll(tblColDose1VC, tblColDose1DT);


                TableColumn<Recipient, String> tblColDose2VC = new TableColumn<>("Location");
                tblColDose2VC.setCellValueFactory(cellData -> {
                    String vc = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 1) {
                        vc = cellData.getValue().getAppointments().get(1).getVaccinationCenter();
                    }
                    return new SimpleStringProperty(vc);
                });

                TableColumn<Recipient, String> tblColDose2DT = new TableColumn<>("Time");
                tblColDose2DT.setCellValueFactory(cellData -> {
                    String dt = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 1) {
                        if (cellData.getValue().getAppointments().get(1).getAppointmentDT() != null) {
                            dt = cellData.getValue().getAppointments().get(1).getAppointmentDT().toString().replace('T', ' ');
                        }
                    }
                    return new SimpleStringProperty(dt);
                });

                TableColumn<Recipient, Appointment> tblColDose2 = new TableColumn<>("Dose 2");
                tblColDose2.getColumns().addAll(tblColDose2VC, tblColDose2DT);

                TableView<Recipient> tblViewRecipient = new TableView<>();
                tblViewRecipient.setItems(FXCollections.observableArrayList(recipients));
                tblViewRecipient.getColumns().addAll(tblColName, tblColPhoneNo, tblColVCStatus, tblColDose1, tblColDose2);


                tblViewRecipient.setRowFactory(tv -> {
                    TableRow<Recipient> row = new TableRow<>();
                    row.setOnMouseClicked(e -> {
                        if (e.getClickCount() == 2 && (!row.isEmpty())) {
                            Recipient rowData = row.getItem();
                            AlertBox.displayFromLayout("Recipient Detail", new RecipientDetailLayout(rowData));
                        }
                    });
                    return row;
                });

                
                getChildren().addAll(hBox, tblViewRecipient);
            }
        }

        class VCTableLayout extends VBox {
            private TextField tfNumOfDose;
            private TableView<VaccinationCenter> tableViewVC;
            private ObservableList<VaccinationCenter> vcObservableList;

            public VCTableLayout(ArrayList<VaccinationCenter> vcs) {
                vcObservableList = FXCollections.observableList(vcs);

                setAlignment(Pos.CENTER);
                setPadding(new Insets(20));

                tfNumOfDose = new TextField();
                tfNumOfDose.setPromptText("Number of vaccines");
                Button btDistributeVaccine = new Button("Distribute vaccine");
                Button btDistributeRecipient = new Button("Distribute recipients");

                HBox hBox = new HBox();
                HBox.setHgrow(tfNumOfDose, Priority.ALWAYS);
                hBox.setPadding(new Insets(10, 10, 10, 10));
                hBox.setSpacing(10);
                hBox.getChildren().addAll(tfNumOfDose, btDistributeVaccine, btDistributeRecipient);

                TableColumn<VaccinationCenter, String> tblColName = new TableColumn<>("Name");
                tblColName.setMinWidth(100);
                tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<VaccinationCenter, String> tblCapacityPerDay = new TableColumn<>("Capacity per Day");
                tblCapacityPerDay.setMinWidth(100);
                tblCapacityPerDay.setCellValueFactory(new PropertyValueFactory<>("capacityPerDay"));

                TableColumn<VaccinationCenter, Integer> tblColRemainingVaccine = new TableColumn<>("Remaining Vaccine");
                tblColRemainingVaccine.setMinWidth(100);
                tblColRemainingVaccine.setCellValueFactory(new PropertyValueFactory<>("remainingVaccine"));

                tableViewVC = new TableView<>();
                tableViewVC.setItems(vcObservableList);
                tableViewVC.getColumns().addAll(tblColName, tblCapacityPerDay, tblColRemainingVaccine);
                tableViewVC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                btDistributeVaccine.setOnAction(e -> distributeVaccine());
                btDistributeRecipient.setOnAction(e -> {
                    try {
                        ministryOfHealth.distributeRecipients();
                        tableViewVC.refresh();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                getChildren().addAll(hBox, tableViewVC);
            }

            private void distributeVaccine() {
                try {
                    int numOfDose = Integer.parseInt(tfNumOfDose.getText());
                    ministryOfHealth.distributeVaccines(numOfDose);
                    tableViewVC.refresh();

                } catch (Exception ignored) {

                }
            }
        }
    }

    class VaccinationCenterLayout extends GridPane implements EventHandler<ActionEvent> {
        private VaccinationCenter vaccinationCenter;
        private Button btDashboard, btRecipients, btLogOut;
        private Button[] navButtons;
        private StackPane ctnSubContent;

        VaccinationCenterLayout(VaccinationCenter vaccinationCenter) {
            this.vaccinationCenter = vaccinationCenter;

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(20);
            getColumnConstraints().add(columnConstraints);

            ColumnConstraints columnConstraints2 = new ColumnConstraints();
            columnConstraints2.setPercentWidth(80);
            getColumnConstraints().add(columnConstraints2);

            VBox ctnSubNav = new VBox();
            ctnSubNav.prefWidthProperty().bind(widthProperty());
            ctnSubNav.prefHeightProperty().bind(heightProperty());
            ctnSubNav.setId("nav-pane");
            GridPane.setConstraints(ctnSubNav, 0, 0);

            StackPane ctnRole = new StackPane();
            ctnRole.setAlignment(Pos.CENTER);
            ctnRole.setId("role-pane");
            Label lbRole = new Label(vaccinationCenter.getName());
            lbRole.setPadding(new Insets(0, 0, 50, 0));
            lbRole.getStyleClass().add("menu-head-label");
            ctnRole.getChildren().add(lbRole);

            btDashboard = new Button("Dashboard");
            btDashboard.getStyleClass().addAll("nav-button", "nav-button-active");
            btDashboard.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btDashboard.setOnAction(this);

            btRecipients = new Button("Recipients");
            btRecipients.getStyleClass().add("nav-button");
            btRecipients.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btRecipients.setOnAction(this);

            btLogOut = new Button("Log Out");
            btLogOut.getStyleClass().add("nav-button");
            btLogOut.prefWidthProperty().bind(ctnSubNav.widthProperty());
            btLogOut.setOnAction(this);

            navButtons = new Button[]{btDashboard, btRecipients, btLogOut};

            ctnSubNav.getChildren().addAll(ctnRole, btDashboard, btRecipients, btLogOut);

            ctnSubContent = new StackPane();
            ctnSubContent.setAlignment(Pos.CENTER);
            setColumnIndex(ctnSubContent, 1);

            ctnSubContent.getChildren().add(new DashboardLayout(vaccinationCenter.getRecipients()));
            getChildren().addAll(ctnSubNav, ctnSubContent);
        }

        @Override
        public void handle(ActionEvent event) {
            ctnSubContent.getChildren().clear();
            if (event.getSource() == btDashboard) {
                changeTab(0);
                ctnSubContent.getChildren().add(new DashboardLayout(vaccinationCenter.getRecipients()));
            } else if (event.getSource() == btRecipients) {
                changeTab(1);
                ctnSubContent.getChildren().add(new RecipientTableLayout(vaccinationCenter.getRecipients()));
            } else if (event.getSource() == btLogOut) {
                changeTab(2);
                window.setScene(new CustomScene(new MainMenuLayout(), 1000, 600));
            }
        }

        private void changeTab(int tabIndex) {
            for (Button navButton : navButtons) {
                navButton.getStyleClass().remove("nav-button-active");
            }
            navButtons[tabIndex].getStyleClass().add("nav-button-active");
        }

        class DashboardLayout extends GridPane {
            ArrayList<Recipient> recipients;

            public DashboardLayout(ArrayList<Recipient> recipients) {
                this.recipients = recipients;

                setPadding(new Insets(50, 0, 0, 75));
                setVgap(80);
                setHgap(40);

                String percentage = String.format("%.2f%%\nfully vaccined", getPercentageOverTotalRecipients(getTotalFullyVaccinedRecipient()));
                Button bt1 = new Button(percentage);
                bt1.setStyle("-fx-background-color: #004c40");
                bt1.getStyleClass().add("dashboard-button");
                setConstraints(bt1, 0, 0);

                percentage = String.format("%.2f%%\nreceived one dose only", getPercentageOverTotalRecipients(getTotalRecipientWithOneDose()));
                Button bt3 = new Button(percentage);
                bt3.setStyle("-fx-background-color: #003c8f");
                bt3.getStyleClass().add("dashboard-button");
                setConstraints(bt3, 1, 0);

                percentage = String.format("%d\ndoses given today", getTotalDoseGivenToday());
                Button bt2 = new Button(percentage);
                bt2.setStyle("-fx-background-color: #7f0000");
                bt2.getStyleClass().add("dashboard-button");
                setConstraints(bt2, 0, 1);

                Button bt4 = new Button(getTotalDoseGivenThisMonth() + "\ndoses given this month");
                bt4.setStyle("-fx-background-color: #003c8f");
                bt4.getStyleClass().add("dashboard-button");
                setConstraints(bt4, 1, 1);
                getChildren().addAll(bt1, bt2, bt3, bt4);
            }

            private long getTotalDoseGivenToday() {
                long count = 0;
                LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                for (Recipient recipient : recipients) {
                    if ((recipient.getVaccinationStatus() == 5 && recipient.getAppointments().get(1).getAppointmentDT()
                            .truncatedTo(ChronoUnit.DAYS).isEqual(today))
                            || (recipient.getVaccinationStatus() == 3 && recipient.getAppointments().get(0).getAppointmentDT()
                            .truncatedTo(ChronoUnit.DAYS).isEqual(today)))
                        count++;
                }
                return count;
            }

            private long getTotalDoseGivenThisMonth() {
                long count = 0;
                LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                for (Recipient recipient : recipients) {
                    if ((recipient.getVaccinationStatus() == 5 && recipient.getAppointments().get(1).getAppointmentDT()
                            .getMonthValue() == today.getMonthValue())
                            || (recipient.getVaccinationStatus() == 3 && recipient.getAppointments().get(0).getAppointmentDT()
                            .getMonthValue() == today.getMonthValue()))
                        count++;
                }
                return count;
            }

            private long getTotalRecipientWithOneDose() { // get number of people with 1 dose only
                long count = 0;
                for (Recipient recipient : recipients) {
                    if (recipient.getVaccinationStatus() == 3)
                        count += 1;
                }
                return count;
            }

            private long getTotalFullyVaccinedRecipient() {
                long count = 0;
                for (Recipient recipient : recipients) {
                    if (recipient.getVaccinationStatus() == 5)
                        count += 1;
                }
                return count;
            }

            private double getPercentageOverTotalRecipients(long totalFilteredRecipients) {
                long totalRecipients = recipients.size();
                return (1.0 * totalFilteredRecipients / totalRecipients) * 100;
            }

        }

        class RecipientTableLayout extends VBox {
            private TableView<Recipient> tblViewRecipient;

            public RecipientTableLayout(ArrayList<Recipient> recipients) {
                setAlignment(Pos.CENTER);
                setPadding(new Insets(20));

                Label label = new Label();
                label.setPadding(new Insets(20));

                TextField tfName = new TextField();
                tfName.setPromptText("Enter name or phone no");
                Button btShowDetail = new Button("Show");
                Button btSetAppointment = new Button("Set Appointment");

                HBox hBox = new HBox();
                hBox.setPadding(new Insets(10, 10, 10, 10));
                hBox.setSpacing(10);
                hBox.getChildren().addAll(tfName, btShowDetail, btSetAppointment);

                TableColumn<Recipient, String> tblColName = new TableColumn<>("Name");
                tblColName.setMinWidth(100);
                tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Recipient, String> tblColPhoneNo = new TableColumn<>("Phone No");
                tblColPhoneNo.setMinWidth(100);
                tblColPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

                TableColumn<Recipient, Integer> tblColVCStatus = new TableColumn<>("Status");
                tblColVCStatus.setMinWidth(100);
                tblColVCStatus.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

                TableColumn<Recipient, String> tblColDose1VC = new TableColumn<>("Location");
                tblColDose1VC.setCellValueFactory(cellData -> {
                    String vc = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 0) {
                        vc = cellData.getValue().getAppointments().get(0).getVaccinationCenter();
                    }
                    return new SimpleStringProperty(vc);
                });

                TableColumn<Recipient, String> tblColDose1DT = new TableColumn<>("Time");
                tblColDose1DT.setCellValueFactory(cellData -> {
                    String dt = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 0) {
                        if (cellData.getValue().getAppointments().get(0).getAppointmentDT() != null) {

                            dt = cellData.getValue().getAppointments().get(0).getAppointmentDT().toString().replace('T', ' ');
                        }
                    }
                    return new SimpleStringProperty(dt);
                });

                TableColumn<Recipient, Appointment> tblColDose1 = new TableColumn<>("Dose 1");
                tblColDose1.getColumns().addAll(tblColDose1VC, tblColDose1DT);

                TableColumn<Recipient, String> tblColDose2VC = new TableColumn<>("Location");
                tblColDose2VC.setCellValueFactory(cellData -> {
                    String vc = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 1) {
                        vc = cellData.getValue().getAppointments().get(1).getVaccinationCenter();
                    }
                    return new SimpleStringProperty(vc);
                });

                TableColumn<Recipient, String> tblColDose2DT = new TableColumn<>("Time");
                tblColDose2DT.setCellValueFactory(cellData -> {
                    String dt = "-";
                    if (cellData.getValue().getAppointments() != null && cellData.getValue().getAppointments().size() > 1) {
                        if (cellData.getValue().getAppointments().get(1).getAppointmentDT() != null) {
                            dt = cellData.getValue().getAppointments().get(1).getAppointmentDT().toString().replace('T', ' ');
                        }
                    }
                    return new SimpleStringProperty(dt);
                });

                TableColumn<Recipient, Appointment> tblColDose2 = new TableColumn<>("Dose 2");
                tblColDose2.getColumns().addAll(tblColDose2VC, tblColDose2DT);

                TableColumn<Recipient, Boolean> tblColAction = new TableColumn<>("");
                tblColAction.setSortable(false);

                tblColAction.setCellFactory(
                        p -> {
                            try {
                                return new ButtonCell();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });

                tblViewRecipient = new TableView<>();
                tblViewRecipient.setItems(FXCollections.observableArrayList(recipients));
                tblViewRecipient.getColumns().addAll(tblColName, tblColPhoneNo, tblColVCStatus, tblColDose1, tblColDose2, tblColAction);


                tblViewRecipient.setRowFactory(tv -> {
                    TableRow<Recipient> row = new TableRow<>();
                    row.setOnMouseClicked(e -> {
                        if (e.getClickCount() == 2 && (!row.isEmpty())) {
                            Recipient rowData = row.getItem();
                            AlertBox.displayFromLayout("Recipient Detail", new RecipientDetailLayout(rowData));
                        }
                    });
                    return row;
                });

                tfName.textProperty().addListener((v, oldValue, newValue) -> {
                    ObservableList<Recipient> allRecipient = tblViewRecipient.getItems();
                    allRecipient.stream().filter(item -> item.getName().startsWith(newValue) || item.getPhoneNumber().startsWith(newValue)).findAny().ifPresent(item -> {
                        tblViewRecipient.getSelectionModel().select(item);
                        tblViewRecipient.scrollTo(item);
                    });
                });

                btShowDetail.setOnAction(e -> {
                    if (tblViewRecipient.getSelectionModel().getSelectedItem() != null) {
                        Recipient r = tblViewRecipient.getSelectionModel().getSelectedItem();
                        AlertBox.displayFromLayout("Recipient Detail", new RecipientDetailLayout(r));
                    }
                });
                btSetAppointment.setOnAction(e -> {
                    try {
                        vaccinationCenter.setAppointmentDate();
                        tblViewRecipient.refresh();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                HBox.setHgrow(tfName, Priority.ALWAYS);
                getChildren().addAll(hBox, tblViewRecipient);
            }

            class ButtonCell extends TableCell<Recipient, Boolean> {
                Button btnCell;

                ButtonCell() throws IOException {
                    btnCell = new Button("+");
                    btnCell.setMaxWidth(10);
                    btnCell.setOnAction(e -> btnCellHandler());
                }

                private void btnCellHandler() {
                    if (tblViewRecipient.getItems().get(super.getIndex()).getVaccinationStatus() < 5) {
                        tblViewRecipient.getItems().get(super.getIndex()).increaseVaccinationStatus();
                        tblViewRecipient.refresh();
                    }
                }

                @Override
                protected void updateItem(Boolean t, boolean empty) {
                    super.updateItem(t, empty);
                    if (!empty) {
                        setGraphic(btnCell);
                    }
                }
            }
        }
    }
}







































