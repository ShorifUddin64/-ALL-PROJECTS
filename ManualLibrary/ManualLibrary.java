
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ManualLibrary extends JFrame {

  // We are using this to color the Library Environment
  private final Color PRIMARY_COLOR = new Color(0x29AB87);    // Dark cyan - lime green
  private final Color PRIMARY_HOVER = new Color(0x4CD4AF);    // Moderate cyan - lime green
  private final Color SECONDARY_COLOR = new Color(0x679289);  // Mostly desaturated dark cyan
  private final Color SECONDARY_HOVER = new Color(0x47655F);  // Very dark grayish cyan
  private final Color DANGER_COLOR = new Color(0xEF4444);     // Red
  private final Color DANGER_HOVER = new Color(0xDC2626);     // Darker Red
  private final Color WARNING_COLOR = new Color(0xF59E0B);    // Orange
  private final Color WARNING_HOVER = new Color(0xD97706);    // Darker Orange
  private final Color BG_COLOR = new Color(0xFCFAF8);         // Light grayish orange
  private final Color CARD_COLOR = Color.WHITE;               // White
  private final Color TEXT_PRIMARY = new Color(0x1F2937);     // Dark Gray
  private final Color TEXT_SECONDARY = new Color(0x6B7280);   // Medium Gray
  private final Color BORDER_COLOR = new Color(0xE5E7EB);     // Light Border

  private List<Book> books;
  private List<Member> members;
  private List<String> categories;
  private Map<String, String> userCredentials = new HashMap<>();
  private List<BorrowingTransaction> transactionHistory = new ArrayList<>();

  private BookTableModel bookTableModel;
  private MemberTableModel memberTableModel;
  private CategoryTableModel categoryTableModel;

  private boolean loggedIn = false;
  private String loggedUser = "";

  private JButton loginButton;
  private JButton signupButton;
  private JLabel userLabel;
  private JTextField searchField;
  private JTable bookTable;
  private JPanel cartPanel;
  private JLabel cartInfoLabel;
  private JCheckBox historyCheckBox;
  private JTable historyTable;
  private JPanel historyPanel;
  private JComboBox<String> categoryFilter;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public ManualLibrary() {
    loadUserCredentials();
    setTitle("ManualLibrary - Read Your Dream Books");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1300, 800);
    setLocationRelativeTo(null);
    getContentPane().setBackground(BG_COLOR);
    
    // Set modern look and feel
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    initData();
    initUI();
  }

  private void initData() {
    // Initialize categories
    categories = new ArrayList<>();
    categories.add("Fiction");
    categories.add("Non-Fiction");
    categories.add("Science Fiction");
    categories.add("Mystery");
    categories.add("Romance");
    categories.add("Biography");
    categories.add("History");
    categories.add("Technology");

    // Initialize books with categories
    books = new ArrayList<>();
    books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 15.99, 10, "Fiction"));
    books.add(new Book("To Kill a Mockingbird", "Harper Lee", 12.99, 8, "Fiction"));
    books.add(new Book("1984", "George Orwell", 14.49, 15, "Science Fiction"));
    books.add(new Book("Pride and Prejudice", "Jane Austen", 9.99, 20, "Romance"));
    books.add(new Book("The Catcher in the Rye", "J.D. Salinger", 13.50, 12, "Fiction"));
    books.add(new Book("Dune", "Frank Herbert", 16.99, 7, "Science Fiction"));
    books.add(new Book("The Martian", "Andy Weir", 13.98, 9, "Science Fiction"));
    books.add(new Book("Gone Girl", "Gillian Flynn", 14.99, 6, "Mystery"));
    books.add(new Book("Steve Jobs", "Walter Isaacson", 18.99, 5, "Biography"));
    books.add(new Book("Sapiens", "Yuval Noah Harari", 17.50, 11, "History"));

    members = new ArrayList<>();
    members.add(new Member("Alice Johnson", "alice@example.com", "Premium"));
    members.add(new Member("Bob Smith", "bob@example.com", "Standard"));
    members.add(new Member("Charlie Brown", "charlie@example.com", "Premium"));
    members.add(new Member("Diana Prince", "diana@example.com", "Standard"));

    userCredentials.put("user", "password");
    userCredentials.put("admin", "admin123");
  }

  private void initUI() {
    setLayout(new BorderLayout(0, 0));

    // Header Panel
    JPanel headerPanel = createHeaderPanel();
    add(headerPanel, BorderLayout.NORTH);

    // Main Content with Tabs
    JTabbedPane tabbedPane = createModernTabbedPane();
    add(tabbedPane, BorderLayout.CENTER);

    // Footer Panel
    JPanel footerPanel = createFooterPanel();
    add(footerPanel, BorderLayout.SOUTH);
  }

  private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(CARD_COLOR);
    headerPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
        new EmptyBorder(20, 30, 20, 30)
    ));

    // Left side - Logo and Search
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
    leftPanel.setBackground(CARD_COLOR);
    
    // Logo
    JLabel logoLabel = new JLabel("ManualLibrary");
    logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
    logoLabel.setForeground(PRIMARY_COLOR);
    leftPanel.add(logoLabel);

    // Search Panel
    JPanel searchPanel = createSearchPanel();
    leftPanel.add(searchPanel);

    headerPanel.add(leftPanel, BorderLayout.WEST);

    // Right side - User controls
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    rightPanel.setBackground(CARD_COLOR);

    loginButton = createModernButton("Login", PRIMARY_COLOR, PRIMARY_HOVER);
    loginButton.addActionListener(e -> showLoginDialog());
    rightPanel.add(loginButton);

    signupButton = createModernButton("Sign Up", SECONDARY_COLOR, SECONDARY_HOVER);
    signupButton.addActionListener(e -> showSignupDialog());
    rightPanel.add(signupButton);

    userLabel = new JLabel();
    userLabel.setForeground(PRIMARY_COLOR);
    userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    userLabel.setVisible(false);
    rightPanel.add(userLabel);

    JButton logoutButton = createModernButton("Logout", DANGER_COLOR, DANGER_HOVER);
    logoutButton.addActionListener(e -> logout());
    logoutButton.setVisible(false);
    rightPanel.add(logoutButton);

    headerPanel.add(rightPanel, BorderLayout.EAST);

    return headerPanel;
  }

  private JPanel createSearchPanel() {
    JPanel searchPanel = new JPanel();
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
    searchPanel.setBackground(CARD_COLOR);
    searchPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER_COLOR, 1),
        new EmptyBorder(8, 12, 8, 12)
    ));

    JLabel searchIcon = new JLabel();
    searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    searchPanel.add(searchIcon);

    searchField = new JTextField(25);
    searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    searchField.setForeground(TEXT_PRIMARY);
    searchField.setBorder(null);
    searchField.setBackground(CARD_COLOR);
    searchField.addActionListener(e -> doSearch());
    searchField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        doSearch();
      }
    });
    searchPanel.add(Box.createHorizontalStrut(8));
    searchPanel.add(searchField);

    return searchPanel;
  }

  private JTabbedPane createModernTabbedPane() {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setBackground(BG_COLOR);
    tabbedPane.setForeground(TEXT_PRIMARY);
    tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
    tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));

    // Custom tab design
    tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
      @Override
      protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                       int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isSelected) {
          g2d.setColor(PRIMARY_COLOR);
        } else {
          g2d.setColor(CARD_COLOR);
        }
        g2d.fillRoundRect(x, y, w, h, 8, 8);
      }

      @Override
      protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                              int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        g.setColor(isSelected ? Color.WHITE : TEXT_PRIMARY);
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
      }
    });

    tabbedPane.addTab("Home", createHomePanel());
    tabbedPane.addTab("Books", createBooksPanel());
    tabbedPane.addTab("Categories", createCategoriesPanel());
    tabbedPane.addTab("Members", createMembersPanel());

    return tabbedPane;
  }

  private JButton createModernButton(String text, Color bgColor, Color hoverColor) {
    JButton btn = new JButton(text) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint rounded background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        // Paint text
        g2d.setColor(getForeground());
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent()) / 2 - 2;
        g2d.drawString(getText(), textX, textY);
      }
    };
    
    btn.setBackground(bgColor);
    btn.setForeground(Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setContentAreaFilled(false);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setPreferredSize(new Dimension(120, 40));
    
    btn.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) { 
        btn.setBackground(hoverColor); 
        btn.repaint();
      }
      public void mouseExited(MouseEvent e) { 
        btn.setBackground(bgColor); 
        btn.repaint();
      }
    });
    
    return btn;
  }

  private JPanel createFooterPanel() {
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    footerPanel.setBackground(CARD_COLOR);
    footerPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
        new EmptyBorder(15, 0, 15, 0)
    ));

    JLabel footerLabel = new JLabel("© 2026 MnualLibrary - Read Your Dream Books");
    footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    footerLabel.setForeground(TEXT_SECONDARY);
    footerPanel.add(footerLabel);

    return footerPanel;
  }

  private void doSearch() {
    String query = searchField.getText().trim().toLowerCase();
    if (query.isEmpty()) {
      bookTableModel.setBooks(books);
    } else {
      List<Book> filtered = new ArrayList<>();
      for (Book b : books) {
        if (b.getTitle().toLowerCase().contains(query) || 
            b.getAuthor().toLowerCase().contains(query) ||
            b.getCategory().toLowerCase().contains(query)) {
          filtered.add(b);
        }
      }
      bookTableModel.setBooks(filtered);
    }
    bookTableModel.fireTableDataChanged();
    updateCartInfo();
  }

  private void logout() {
    loggedIn = false;
    loggedUser = "";
    userLabel.setVisible(false);
    loginButton.setVisible(true);
    signupButton.setVisible(true);
    
    // Find and hide logout button
    Container parent = loginButton.getParent();
    for (Component comp : parent.getComponents()) {
      if (comp instanceof JButton && ((JButton) comp).getText().contains("Logout")) {
        comp.setVisible(false);
        break;
      }
    }
    
    bookTableModel.fireTableDataChanged();
    updateCartInfo();
    JOptionPane.showMessageDialog(this, "Successfully logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
  }

  // Book model with category
  class Book {
    private String title;
    private String author;
    private double price;
    private int stockQuantity;
    private int borrowedQuantity;
    private String category;

    public Book(String title, String author, double price, int stockQuantity, String category) {
      this.title = title;
      this.author = author;
      this.price = price;
      this.stockQuantity = stockQuantity;
      this.borrowedQuantity = 0;
      this.category = category;
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int qty) { stockQuantity = qty; }
    public int getBorrowedQuantity() { return borrowedQuantity; }
    public void setBorrowedQuantity(int qty) { borrowedQuantity = qty; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
  }

  // Enhanced Member model
  class Member {
    private String name;
    private String email;
    private String membershipType;

    public Member(String name, String email, String membershipType) {
      this.name = name;
      this.email = email;
      this.membershipType = membershipType;
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String type) { this.membershipType = type; }
  }

  // Borrowing transaction class
  class BorrowingTransaction {
    private final String user;
    private final String bookTitle;
    private final String action;
    private final int quantity;
    private final double price;
    private final Date date;

    public BorrowingTransaction(String user, String bookTitle, String action, int quantity, double price, Date date) {
      this.user = user;
      this.bookTitle = bookTitle;
      this.action = action;
      this.quantity = quantity;
      this.price = price;
      this.date = date;
    }
    
    public String getUser() { return user; }
    public String getBookTitle() { return bookTitle; }
    public String getAction() { return action; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return price * quantity; }
    public String getDateString() { return dateFormat.format(date); }
  }
  // Table Models
  class BookTableModel extends AbstractTableModel {
    private List<Book> displayBooks;
    private final String[] columnNames = {"Title", "Author", "Category", "Price/Day", "Total Value", "Stock", "Borrowed", "Actions"};

    public BookTableModel(List<Book> books) {
      this.displayBooks = new ArrayList<>(books);
    }

    public void setBooks(List<Book> books) {
      this.displayBooks = new ArrayList<>(books);
    }

    @Override
    public int getRowCount() { return displayBooks.size(); }
    
    @Override
    public int getColumnCount() { return columnNames.length; }
    
    @Override
    public String getColumnName(int column) { return columnNames[column]; }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Book book = displayBooks.get(rowIndex);
      switch (columnIndex) {
        case 0: return book.getTitle();
        case 1: return book.getAuthor();
        case 2: return book.getCategory();
        case 3: return String.format("$%.2f", book.getPrice());
        case 4: return String.format("$%.2f", book.getPrice() * book.getBorrowedQuantity());
        case 5: return book.getStockQuantity();
        case 6: return book.getBorrowedQuantity();
        case 7: return "Actions";
        default: return null;
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return columnIndex == 7; // Only actions column is editable (last column)
    }

    public Book getBookAt(int rowIndex) {
      return displayBooks.get(rowIndex);
    }
  }

  class MemberTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Email", "Membership", "Actions"};

    @Override
    public int getRowCount() { return members.size(); }
    
    @Override
    public int getColumnCount() { return columnNames.length; }
    
    @Override
    public String getColumnName(int column) { return columnNames[column]; }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Member member = members.get(rowIndex);
      switch (columnIndex) {
        case 0: return member.getName();
        case 1: return member.getEmail();
        case 2: return member.getMembershipType();
        case 3: return "Actions";
        default: return null;
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return columnIndex == 3;
    }
  }

  class CategoryTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Category", "Book Count", "Actions"};

    @Override
    public int getRowCount() { return categories.size(); }
    
    @Override
    public int getColumnCount() { return columnNames.length; }
    
    @Override
    public String getColumnName(int column) { return columnNames[column]; }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      String category = categories.get(rowIndex);
      switch (columnIndex) {
        case 0: return category;
        case 1: return countBooksInCategory(category);
        case 2: return "Actions";
        default: return null;
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return columnIndex == 2;
    }

    private int countBooksInCategory(String category) {
      return (int) books.stream().filter(book -> book.getCategory().equals(category)).count();
    }
  }

  // Panel Creation Methods
  private JPanel createHomePanel() {
    JPanel homePanel = new JPanel(new BorderLayout());
    homePanel.setBackground(BG_COLOR);
    homePanel.setBorder(new EmptyBorder(30, 30, 30, 30));

    // Welcome Card
    JPanel welcomeCard = createWelcomeCard();
    homePanel.add(welcomeCard, BorderLayout.NORTH);

    // Statistics Panel
    JPanel statsPanel = createStatsPanel();
    homePanel.add(statsPanel, BorderLayout.CENTER);

    // Cart Panel
    cartPanel = createCartPanel();
    homePanel.add(cartPanel, BorderLayout.SOUTH);

    return homePanel;
  }

  private JPanel createWelcomeCard() {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(CARD_COLOR);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER_COLOR, 1),
        new EmptyBorder(40, 40, 40, 40)
    ));

    JLabel welcomeLabel = new JLabel("Welcome to ManualLibrary!");
    welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
    welcomeLabel.setForeground(PRIMARY_COLOR);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitleLabel = new JLabel("Your Digital Gateway to Knowledge");
    subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    subtitleLabel.setForeground(TEXT_SECONDARY);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    card.add(welcomeLabel);
    card.add(Box.createVerticalStrut(10));
    card.add(subtitleLabel);

    return card;
  }

  private JPanel createStatsPanel() {
    JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
    statsPanel.setBackground(BG_COLOR);
    statsPanel.setBorder(new EmptyBorder(30, 0, 30, 0));

    // Create stat cards
    statsPanel.add(createStatCard("Total Books", String.valueOf(books.size()), PRIMARY_COLOR));
    statsPanel.add(createStatCard("Members", String.valueOf(members.size()), SECONDARY_COLOR));
    statsPanel.add(createStatCard("Categories", String.valueOf(categories.size()), WARNING_COLOR));
    
    int totalBorrowed = books.stream().mapToInt(Book::getBorrowedQuantity).sum();
    statsPanel.add(createStatCard("Borrowed", String.valueOf(totalBorrowed), DANGER_COLOR));

    return statsPanel;
  }

  private JPanel createStatCard(String title, String value, Color accentColor) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(CARD_COLOR);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER_COLOR, 1),
        new EmptyBorder(30, 20, 30, 20)
    ));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    titleLabel.setForeground(TEXT_SECONDARY);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
    valueLabel.setForeground(accentColor);
    valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    card.add(titleLabel);
    card.add(Box.createVerticalStrut(10));
    card.add(valueLabel);

    return card;
  }

  private JPanel createCartPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(CARD_COLOR);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(BORDER_COLOR, 1),
        new EmptyBorder(20, 30, 20, 30)
    ));

    JLabel cartTitle = new JLabel("Borrowing Cart");
    cartTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
    cartTitle.setForeground(PRIMARY_COLOR);

    cartInfoLabel = new JLabel("No items in cart");
    cartInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    cartInfoLabel.setForeground(TEXT_SECONDARY);

    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftPanel.setBackground(CARD_COLOR);
    leftPanel.add(cartTitle);
    leftPanel.add(Box.createHorizontalStrut(20));
    leftPanel.add(cartInfoLabel);

    JButton clearCartButton = createModernButton("Clear Cart", DANGER_COLOR, DANGER_HOVER);
    clearCartButton.addActionListener(e -> clearCart());

    panel.add(leftPanel, BorderLayout.WEST);
    panel.add(clearCartButton, BorderLayout.EAST);

    return panel;
  }

  private JPanel createBooksPanel() {
    JPanel booksPanel = new JPanel(new BorderLayout());
    booksPanel.setBackground(BG_COLOR);
    booksPanel.setBorder(new EmptyBorder(20, 30, 30, 30));

    // Header with controls
    JPanel headerPanel = createBooksHeaderPanel();
    booksPanel.add(headerPanel, BorderLayout.NORTH);

    // Books table
    bookTableModel = new BookTableModel(books);
    bookTable = createModernTable(bookTableModel);
    bookTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
    bookTable.getColumn("Actions").setCellEditor(new BookButtonEditor());

    JScrollPane scrollPane = new JScrollPane(bookTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    scrollPane.getViewport().setBackground(CARD_COLOR);
    booksPanel.add(scrollPane, BorderLayout.CENTER);

    // History panel
    historyPanel = createHistoryPanel();
    booksPanel.add(historyPanel, BorderLayout.SOUTH);

    return booksPanel;
  }

  private JPanel createBooksHeaderPanel() {
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    headerPanel.setBackground(BG_COLOR);

    JButton addBookButton = createModernButton("Add Book", PRIMARY_COLOR, PRIMARY_HOVER);
    addBookButton.addActionListener(e -> showAddBookDialog());
    headerPanel.add(addBookButton);

    JLabel filterLabel = new JLabel("Filter by Category:");
    filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    filterLabel.setForeground(TEXT_PRIMARY);
    headerPanel.add(filterLabel);

    categoryFilter = new JComboBox<>();
    categoryFilter.addItem("All Categories");
    for (String category : categories) {
      categoryFilter.addItem(category);
    }
    categoryFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    categoryFilter.addActionListener(e -> filterByCategory());
    headerPanel.add(categoryFilter);

    historyCheckBox = new JCheckBox("Show Transaction History");
    historyCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    historyCheckBox.setBackground(BG_COLOR);
    historyCheckBox.addActionListener(e -> toggleHistoryPanel());
    headerPanel.add(historyCheckBox);

    return headerPanel;
  }

  private JPanel createHistoryPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(CARD_COLOR);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
        new EmptyBorder(20, 0, 0, 0)
    ));
    panel.setVisible(false);

    // Header panel with title and summary
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(CARD_COLOR);
    headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

    JLabel historyTitle = new JLabel("Transaction History");
    historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    historyTitle.setForeground(PRIMARY_COLOR);
    headerPanel.add(historyTitle, BorderLayout.WEST);

    JLabel totalValueLabel = new JLabel("Total Value: $0.00");
    totalValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    totalValueLabel.setForeground(TEXT_SECONDARY);
    headerPanel.add(totalValueLabel, BorderLayout.EAST);

    panel.add(headerPanel, BorderLayout.NORTH);

    // History table with enhanced columns
    String[] historyColumns = {"User", "Book", "Action", "Quantity", "Price/Day", "Total", "Date"};
    DefaultTableModel historyModel = new DefaultTableModel(historyColumns, 0);
    historyTable = createModernTable(historyModel);
    
    // Set column widths
    historyTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Price/Day
    historyTable.getColumnModel().getColumn(5).setPreferredWidth(80); // Total
    
    JScrollPane historyScroll = new JScrollPane(historyTable);
    historyScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    historyScroll.setPreferredSize(new Dimension(0, 150));
    panel.add(historyScroll, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createCategoriesPanel() {
    JPanel categoriesPanel = new JPanel(new BorderLayout());
    categoriesPanel.setBackground(BG_COLOR);
    categoriesPanel.setBorder(new EmptyBorder(20, 30, 30, 30));

    // Header
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    headerPanel.setBackground(BG_COLOR);
    
    JButton addCategoryButton = createModernButton("Add Category", SECONDARY_COLOR, SECONDARY_HOVER);
    addCategoryButton.addActionListener(e -> showAddCategoryDialog());
    headerPanel.add(addCategoryButton);
    
    categoriesPanel.add(headerPanel, BorderLayout.NORTH);

    // Categories table
    categoryTableModel = new CategoryTableModel();
    JTable categoryTable = createModernTable(categoryTableModel);
    categoryTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
    categoryTable.getColumn("Actions").setCellEditor(new CategoryButtonEditor());

    JScrollPane scrollPane = new JScrollPane(categoryTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    categoriesPanel.add(scrollPane, BorderLayout.CENTER);

    return categoriesPanel;
  }

  private JPanel createMembersPanel() {
    JPanel membersPanel = new JPanel(new BorderLayout());
    membersPanel.setBackground(BG_COLOR);
    membersPanel.setBorder(new EmptyBorder(20, 30, 30, 30));

    // Header
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    headerPanel.setBackground(BG_COLOR);
    
    JButton addMemberButton = createModernButton("Add Member", WARNING_COLOR, WARNING_HOVER);
    addMemberButton.addActionListener(e -> showAddMemberDialog());
    headerPanel.add(addMemberButton);
    
    membersPanel.add(headerPanel, BorderLayout.NORTH);

    // Members table
    memberTableModel = new MemberTableModel();
    JTable memberTable = createModernTable(memberTableModel);
    memberTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
    memberTable.getColumn("Actions").setCellEditor(new MemberButtonEditor());

    JScrollPane scrollPane = new JScrollPane(memberTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
    membersPanel.add(scrollPane, BorderLayout.CENTER);

    return membersPanel;
  }

  private JTable createModernTable(AbstractTableModel model) {
    JTable table = new JTable(model);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.setRowHeight(45);
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 1));
    table.setBackground(CARD_COLOR);
    table.setSelectionBackground(new Color(0xE3F2FD));
    table.setSelectionForeground(TEXT_PRIMARY);
    
    // Header styling
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    table.getTableHeader().setBackground(new Color(0xF5F5F5));
    table.getTableHeader().setForeground(TEXT_PRIMARY);
    table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
    
    return table;
  }

  // Button Renderers and Editors
  class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton borrowButton;
    private final JButton returnButton;
    private final JButton editButton;
    private final JButton deleteButton;

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setBackground(CARD_COLOR);
        
        borrowButton = createSmallButton("Borrow", PRIMARY_COLOR);
        returnButton = createSmallButton("Return", SECONDARY_COLOR);
        editButton = createSmallButton("Edit", WARNING_COLOR);
        deleteButton = createSmallButton("Delete", DANGER_COLOR);
        
        borrowButton.setToolTipText("Borrow this book (Price/day applies)");
        returnButton.setToolTipText("Return borrowed copies");
        editButton.setToolTipText("Edit book details");
        deleteButton.setToolTipText("Delete this book");
    }

    private JButton createSmallButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(80, 25));
        return btn;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                               boolean isSelected, boolean hasFocus, 
                                               int row, int column) {
        removeAll();
        
        if (table.getModel() instanceof BookTableModel) {
            add(borrowButton);
            add(returnButton);
            add(editButton);
            add(deleteButton);
        } else {
            add(editButton);
            add(deleteButton);
        }
        
        return this;
    }
}

private final class BookButtonEditor extends DefaultCellEditor {
    private final JPanel panel;
    private final JButton borrowButton;
    private final JButton returnButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private int currentRow;

    public BookButtonEditor() {
        super(new JCheckBox());
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setBackground(CARD_COLOR);
        
        borrowButton = createActionButton("Borrow", PRIMARY_COLOR, e -> borrowBook());
        returnButton = createActionButton("Return", SECONDARY_COLOR, e -> returnBook());
        editButton = createActionButton("Edit", WARNING_COLOR, e -> editBook());
        deleteButton = createActionButton("Delete", DANGER_COLOR, e -> deleteBook());
        
        borrowButton.setToolTipText("Borrow this book (Price/day applies)");
        returnButton.setToolTipText("Return borrowed copies");
        editButton.setToolTipText("Edit book details");
        deleteButton.setToolTipText("Delete this book");
        
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(editButton);
        panel.add(deleteButton);
    }

    private JButton createActionButton(String text, Color color, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(80, 25));
        btn.addActionListener(listener);
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                             boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Actions";
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    private void borrowBook() {
      if (!loggedIn) {
        JOptionPane.showMessageDialog(ManualLibrary.this, "Please login first to borrow books.", "Login Required", JOptionPane.WARNING_MESSAGE);
        return;
      }
      Book book = bookTableModel.getBookAt(currentRow);
      if (book.getStockQuantity() > 0) {
        // Create a custom dialog for borrowing
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField qtyField = new JTextField("1", 10);
        JLabel priceLabel = new JLabel(String.format("Price per day: $%.2f", book.getPrice()));
        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", book.getPrice()));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        panel.add(qtyField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(priceLabel, gbc);
        gbc.gridy = 2;
        panel.add(totalLabel, gbc);

        // Update total when quantity changes
        qtyField.getDocument().addDocumentListener(new DocumentListener() {
          private void updateTotal() {
            try {
              int qty = Integer.parseInt(qtyField.getText().trim());
              totalLabel.setText(String.format("Total: $%.2f", book.getPrice() * qty));
            } catch (NumberFormatException e) {
              totalLabel.setText("Total: $0.00");
            }
          }
          public void insertUpdate(DocumentEvent e) { updateTotal(); }
          public void removeUpdate(DocumentEvent e) { updateTotal(); }
          public void changedUpdate(DocumentEvent e) { updateTotal(); }
        });

        int result = JOptionPane.showConfirmDialog(ManualLibrary.this, panel, 
            "Borrow Book - " + book.getTitle(), JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
          try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            if (qty > 0 && qty <= book.getStockQuantity()) {
              book.setStockQuantity(book.getStockQuantity() - qty);
              book.setBorrowedQuantity(book.getBorrowedQuantity() + qty);
              transactionHistory.add(new BorrowingTransaction(loggedUser, book.getTitle(), "Borrowed", qty, book.getPrice(), new Date()));
              bookTableModel.fireTableDataChanged();
              updateCartInfo();
              updateHistoryTable();
              JOptionPane.showMessageDialog(ManualLibrary.this, 
                  String.format("Book borrowed successfully!\nTotal charge: $%.2f", book.getPrice() * qty), 
                  "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
              JOptionPane.showMessageDialog(ManualLibrary.this, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ManualLibrary.this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      } else {
        JOptionPane.showMessageDialog(ManualLibrary.this, "This book is out of stock!", "Out of Stock", JOptionPane.WARNING_MESSAGE);
      }
      fireEditingStopped();
    }

    private void returnBook() {
      if (!loggedIn) {
        JOptionPane.showMessageDialog(ManualLibrary.this, "Please login first to return books.", "Login Required", JOptionPane.WARNING_MESSAGE);
        return;
      }
      Book book = bookTableModel.getBookAt(currentRow);
      if (book.getBorrowedQuantity() > 0) {
        String qtyStr = JOptionPane.showInputDialog(ManualLibrary.this, "Enter quantity to return:", "1");
        if (qtyStr != null && !qtyStr.trim().isEmpty()) {
          try {
            int qty = Integer.parseInt(qtyStr.trim());
            if (qty > 0 && qty <= book.getBorrowedQuantity()) {
              book.setStockQuantity(book.getStockQuantity() + qty);
              book.setBorrowedQuantity(book.getBorrowedQuantity() - qty);
              transactionHistory.add(new BorrowingTransaction(loggedUser, book.getTitle(), "Returned", qty, book.getPrice(), new Date()));
              bookTableModel.fireTableDataChanged();
              updateCartInfo();
              updateHistoryTable();
              JOptionPane.showMessageDialog(ManualLibrary.this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
              JOptionPane.showMessageDialog(ManualLibrary.this, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            }
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ManualLibrary.this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      } else {
        JOptionPane.showMessageDialog(ManualLibrary.this, "No borrowed copies to return!", "No Borrowed Books", JOptionPane.WARNING_MESSAGE);
      }
      fireEditingStopped();
    }

    private void editBook() {
      Book book = bookTableModel.getBookAt(currentRow);
      showEditBookDialog(book, currentRow);
      fireEditingStopped();
    }

    private void deleteBook() {
      int result = JOptionPane.showConfirmDialog(ManualLibrary.this, 
          "Are you sure you want to delete this book?", "Confirm Delete", 
          JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if (result == JOptionPane.YES_OPTION) {
        Book book = bookTableModel.getBookAt(currentRow);
        books.remove(book);
        bookTableModel.fireTableDataChanged();
        updateCartInfo();
        JOptionPane.showMessageDialog(ManualLibrary.this, 
            "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      fireEditingStopped();
    }
  }

  class CategoryButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton editButton, deleteButton;
    private int currentRow;

    public CategoryButtonEditor() {
      super(new JCheckBox());
      panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
      panel.setBackground(CARD_COLOR);
      
      editButton = createActionButton("Edit", WARNING_COLOR, e -> editCategory());
      deleteButton = createActionButton("Delete", DANGER_COLOR, e -> deleteCategory());
      
      panel.add(editButton);
      panel.add(deleteButton);
    }

    private JButton createActionButton(String text, Color color, ActionListener listener) {
      JButton btn = new JButton(text);
      btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
      btn.setBackground(color);
      btn.setForeground(Color.WHITE);
      btn.setBorderPainted(false);
      btn.setFocusPainted(false);
      btn.setPreferredSize(new Dimension(80, 25));
      btn.addActionListener(listener);
      return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      currentRow = row;
      return panel;
    }

    private void editCategory() {
      String oldCategory = categories.get(currentRow);
      String newCategory = JOptionPane.showInputDialog(ManualLibrary.this, "Enter new category name:", oldCategory);
      if (newCategory != null && !newCategory.trim().isEmpty() && !newCategory.equals(oldCategory)) {
        // Update books with this category
        for (Book book : books) {
          if (book.getCategory().equals(oldCategory)) {
            book.setCategory(newCategory);
          }
        }
        categories.set(currentRow, newCategory);
        categoryTableModel.fireTableDataChanged();
        bookTableModel.fireTableDataChanged();
        updateCategoryFilter();
        JOptionPane.showMessageDialog(ManualLibrary.this, "Category updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      fireEditingStopped();
    }

    private void deleteCategory() {
      String category = categories.get(currentRow);
      long bookCount = books.stream().filter(book -> book.getCategory().equals(category)).count();
      
      if (bookCount > 0) {
        JOptionPane.showMessageDialog(ManualLibrary.this, 
            "Cannot delete category. There are " + bookCount + " books in this category.", 
            "Cannot Delete", JOptionPane.WARNING_MESSAGE);
      } else {
        int result = JOptionPane.showConfirmDialog(ManualLibrary.this, 
            "Are you sure you want to delete this category?", "Confirm Delete", 
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
          categories.remove(currentRow);
          categoryTableModel.fireTableDataChanged();
          updateCategoryFilter();
          JOptionPane.showMessageDialog(ManualLibrary.this, "Category deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      fireEditingStopped();
    }
  }

  class MemberButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton editButton, deleteButton;
    private int currentRow;

    public MemberButtonEditor() {
      super(new JCheckBox());
      panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
      panel.setBackground(CARD_COLOR);
      
      editButton = createActionButton("Edit", WARNING_COLOR, e -> editMember());
      deleteButton = createActionButton("Delete", DANGER_COLOR, e -> deleteMember());
      
      panel.add(editButton);
      panel.add(deleteButton);
    }

    private JButton createActionButton(String text, Color color, ActionListener listener) {
      JButton btn = new JButton(text);
      btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
      btn.setBackground(color);
      btn.setForeground(Color.WHITE);
      btn.setBorderPainted(false);
      btn.setFocusPainted(false);
      btn.setPreferredSize(new Dimension(80, 25));
      btn.addActionListener(listener);
      return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      currentRow = row;
      return panel;
    }

    private void editMember() {
      showEditMemberDialog(currentRow);
      fireEditingStopped();
    }

    private void deleteMember() {
      int result = JOptionPane.showConfirmDialog(ManualLibrary.this, 
          "Are you sure you want to delete this member?", "Confirm Delete", 
          JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if (result == JOptionPane.YES_OPTION) {
        members.remove(currentRow);
        memberTableModel.fireTableDataChanged();
        JOptionPane.showMessageDialog(ManualLibrary.this, "Member deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      fireEditingStopped();
    }
  }

  // Dialog Methods
  private void showLoginDialog() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Username:"), gbc);
    gbc.gridx = 1;
    panel.add(usernameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Password:"), gbc);
    gbc.gridx = 1;
    panel.add(passwordField, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      String username = usernameField.getText().trim();
      String password = new String(passwordField.getPassword());

      if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter username and password!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
        loggedIn = true;
        loggedUser = username;
        userLabel.setText("Welcome, " + username + "!");
        userLabel.setVisible(true);
        loginButton.setVisible(false);
        signupButton.setVisible(false);
        // Show logout button
        Container parent = loginButton.getParent();
        for (Component comp : parent.getComponents()) {
          if (comp instanceof JButton && ((JButton) comp).getText().contains("Logout")) {
            comp.setVisible(true);
            break;
          }
        }
        bookTableModel.fireTableDataChanged();
        updateCartInfo();
        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showSignupDialog() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JPasswordField confirmPasswordField = new JPasswordField(20);

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Username:"), gbc);
    gbc.gridx = 1;
    panel.add(usernameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Password:"), gbc);
    gbc.gridx = 1;
    panel.add(passwordField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Confirm Password:"), gbc);
    gbc.gridx = 1;
    panel.add(confirmPasswordField, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      String username = usernameField.getText().trim();
      String password = new String(passwordField.getPassword());
      String confirmPassword = new String(confirmPasswordField.getPassword());

      if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
      } else if (!isStrongPassword(password)) {
        JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain uppercase, lowercase, and numbers!", "Weak Password", JOptionPane.ERROR_MESSAGE);
      } else if (!password.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
      } else if (userCredentials.containsKey(username)) {
        JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
      } else {
        userCredentials.put(username, password);
        saveUserCredentials();
        JOptionPane.showMessageDialog(this, "Account created successfully! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  private void showAddBookDialog() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField titleField = new JTextField(20);
    JTextField authorField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    JTextField stockField = new JTextField(20);
    JComboBox<String> categoryCombo = new JComboBox<>(categories.toArray(new String[0]));

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Title:"), gbc);
    gbc.gridx = 1;
    panel.add(titleField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Author:"), gbc);
    gbc.gridx = 1;
    panel.add(authorField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Price:"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(new JLabel("Stock:"), gbc);
    gbc.gridx = 1;
    panel.add(stockField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(new JLabel("Category:"), gbc);
    gbc.gridx = 1;
    panel.add(categoryCombo, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      try {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        double price = Double.parseDouble(priceField.getText().trim());
        int stock = Integer.parseInt(stockField.getText().trim());
        String category = (String) categoryCombo.getSelectedItem();

        if (title.isEmpty() || author.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (price < 0) {
          JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (stock < 0) {
          JOptionPane.showMessageDialog(this, "Stock cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          books.add(new Book(title, author, price, stock, category));
          bookTableModel.fireTableDataChanged();
          updateCartInfo();
          JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and stock!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showEditBookDialog(Book book, int index) {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField titleField = new JTextField(book.getTitle(), 20);
    JTextField authorField = new JTextField(book.getAuthor(), 20);
    JTextField priceField = new JTextField(String.valueOf(book.getPrice()), 20);
    JTextField stockField = new JTextField(String.valueOf(book.getStockQuantity()), 20);
    JComboBox<String> categoryCombo = new JComboBox<>(categories.toArray(new String[0]));
    categoryCombo.setSelectedItem(book.getCategory());

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Title:"), gbc);
    gbc.gridx = 1;
    panel.add(titleField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Author:"), gbc);
    gbc.gridx = 1;
    panel.add(authorField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Price:"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    gbc.gridx = 0; gbc.gridy = 3;
    panel.add(new JLabel("Stock:"), gbc);
    gbc.gridx = 1;
    panel.add(stockField, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    panel.add(new JLabel("Category:"), gbc);
    gbc.gridx = 1;
    panel.add(categoryCombo, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      try {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        double price = Double.parseDouble(priceField.getText().trim());
        int stock = Integer.parseInt(stockField.getText().trim());
        String category = (String) categoryCombo.getSelectedItem();

        if (title.isEmpty() || author.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          Book updatedBook = new Book(title, author, price, stock, category);
          updatedBook.setBorrowedQuantity(book.getBorrowedQuantity());
          books.set(books.indexOf(book), updatedBook);
          bookTableModel.fireTableDataChanged();
          updateCartInfo();
          JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and stock!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showAddCategoryDialog() {
    String category = JOptionPane.showInputDialog(this, "Enter new category name:", "Add Category", JOptionPane.PLAIN_MESSAGE);
    if (category != null && !category.trim().isEmpty()) {
      category = category.trim();
      if (!categories.contains(category)) {
        categories.add(category);
        categoryTableModel.fireTableDataChanged();
        updateCategoryFilter();
        JOptionPane.showMessageDialog(this, "Category added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "Category already exists!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showAddMemberDialog() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField nameField = new JTextField(20);
    JTextField emailField = new JTextField(20);
    JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Standard", "Premium"});

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    panel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Membership:"), gbc);
    gbc.gridx = 1;
    panel.add(typeCombo, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Member", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      String name = nameField.getText().trim();
      String email = emailField.getText().trim();
      String membershipType = (String) typeCombo.getSelectedItem();

      if (name.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
      } else if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
      } else {
        members.add(new Member(name, email, membershipType));
        memberTableModel.fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  private void showEditMemberDialog(int index) {
    Member member = members.get(index);
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JTextField nameField = new JTextField(member.getName(), 20);
    JTextField emailField = new JTextField(member.getEmail(), 20);
    JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Standard", "Premium"});
    typeCombo.setSelectedItem(member.getMembershipType());

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    panel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = 2;
    panel.add(new JLabel("Membership:"), gbc);
    gbc.gridx = 1;
    panel.add(typeCombo, gbc);

    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Member", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      String name = nameField.getText().trim();
      String email = emailField.getText().trim();
      String membershipType = (String) typeCombo.getSelectedItem();

      if (name.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
      } else if (!isValidEmail(email)) {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
      } else {
        members.set(index, new Member(name, email, membershipType));
        memberTableModel.fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Member updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  // Utility Methods
  private void filterByCategory() {
    String selectedCategory = (String) categoryFilter.getSelectedItem();
    if ("All Categories".equals(selectedCategory)) {
      bookTableModel.setBooks(books);
    } else {
      List<Book> filtered = new ArrayList<>();
      for (Book book : books) {
        if (book.getCategory().equals(selectedCategory)) {
          filtered.add(book);
        }
      }
      bookTableModel.setBooks(filtered);
    }
    bookTableModel.fireTableDataChanged();
    updateCartInfo();
  }

  private void toggleHistoryPanel() {
    historyPanel.setVisible(historyCheckBox.isSelected());
    if (historyCheckBox.isSelected()) {
      updateHistoryTable();
    }
    revalidate();
    repaint();
  }

  private void updateHistoryTable() {
    DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
    model.setRowCount(0);
    double grandTotal = 0.0;
    
    for (BorrowingTransaction transaction : transactionHistory) {
      double totalPrice = transaction.getTotalPrice();
      if (transaction.getAction().equals("Borrowed")) {
        grandTotal += totalPrice;
      } else if (transaction.getAction().equals("Returned")) {
        grandTotal -= totalPrice;
      }
      
      model.addRow(new Object[]{
        transaction.getUser(),
        transaction.getBookTitle(),
        transaction.getAction(),
        transaction.getQuantity(),
        String.format("$%.2f", transaction.getPrice()),
        String.format("$%.2f", totalPrice),
        transaction.getDateString()
      });
    }
    
    // Update total value label in the header
    Container parent = historyTable.getParent();
    while (parent != null && !(parent instanceof JPanel)) {
      parent = parent.getParent();
    }
    if (parent != null) {
      JPanel historyPanel = (JPanel) parent;
      Component[] components = ((JPanel)historyPanel.getComponent(0)).getComponents();
      for (Component c : components) {
        if (c instanceof JLabel && ((JLabel)c).getText().startsWith("Total Value")) {
          ((JLabel)c).setText(String.format("Total Value: $%.2f", grandTotal));
          break;
        }
      }
    }
  }

  private void updateCartInfo() {
    if (!loggedIn) {
      cartInfoLabel.setText("Please login to view cart");
      return;
    }

    int totalBorrowed = 0;
    double totalPrice = 0.0;
    StringBuilder details = new StringBuilder();
    
    // Calculate totals for current user and build details string
    Map<String, BorrowingDetails> bookDetails = new HashMap<>();
    
    for (BorrowingTransaction transaction : transactionHistory) {
      if (transaction.getUser().equals(loggedUser)) {
        if (transaction.getAction().equals("Borrowed")) {
          String bookTitle = transaction.getBookTitle();
          if (!bookDetails.containsKey(bookTitle)) {
            bookDetails.put(bookTitle, new BorrowingDetails());
          }
          BorrowingDetails detail = bookDetails.get(bookTitle);
          detail.quantity += transaction.getQuantity();
          detail.totalPrice += transaction.getTotalPrice();
          totalBorrowed += transaction.getQuantity();
          totalPrice += transaction.getTotalPrice();
        } else if (transaction.getAction().equals("Returned")) {
          String bookTitle = transaction.getBookTitle();
          if (bookDetails.containsKey(bookTitle)) {
            BorrowingDetails detail = bookDetails.get(bookTitle);
            detail.quantity -= transaction.getQuantity();
            if (detail.quantity <= 0) {
              bookDetails.remove(bookTitle);
            }
          }
        }
      }
    }
    
    if (totalBorrowed > 0) {
      details.append("<html>");
      for (Map.Entry<String, BorrowingDetails> entry : bookDetails.entrySet()) {
        if (entry.getValue().quantity > 0) {
          details.append(String.format("%s (%d) - $%.2f<br>", 
              entry.getKey(), 
              entry.getValue().quantity,
              entry.getValue().totalPrice));
        }
      }
      details.append(String.format("<b>Total: $%.2f</b></html>", totalPrice));
      cartInfoLabel.setText(details.toString());
    } else {
      cartInfoLabel.setText("No items in cart");
    }
  }
  
  private static class BorrowingDetails {
    int quantity = 0;
    double totalPrice = 0;
  }

  private void clearCart() {
    if (!loggedIn) {
      JOptionPane.showMessageDialog(this, "Please login first.", "Login Required", JOptionPane.WARNING_MESSAGE);
      return;
    }

    int totalBorrowed = books.stream().mapToInt(Book::getBorrowedQuantity).sum();
    if (totalBorrowed > 0) {
      int result = JOptionPane.showConfirmDialog(this, 
          "Are you sure you want to return all borrowed books?", "Clear Cart", 
          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (result == JOptionPane.YES_OPTION) {
        for (Book book : books) {
          if (book.getBorrowedQuantity() > 0) {
            book.setStockQuantity(book.getStockQuantity() + book.getBorrowedQuantity());
            transactionHistory.add(new BorrowingTransaction(loggedUser, book.getTitle(), "Returned", book.getBorrowedQuantity(), book.getPrice(), new Date()));
            book.setBorrowedQuantity(0);
          }
        }
        bookTableModel.fireTableDataChanged();
        updateCartInfo();
        updateHistoryTable();
        JOptionPane.showMessageDialog(this, "All books returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(this, "Cart is already empty!", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void updateCategoryFilter() {
    String selected = (String) categoryFilter.getSelectedItem();
    categoryFilter.removeAllItems();
    categoryFilter.addItem("All Categories");
    for (String category : categories) {
      categoryFilter.addItem(category);
    }
    if (selected != null && categories.contains(selected)) {
      categoryFilter.setSelectedItem(selected);
    } else {
      categoryFilter.setSelectedItem("All Categories");
    }
  }

  // Utility: Email validation
  private boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(regex);
  }

  // Utility: Password strength validation
  private boolean isStrongPassword(String password) {
    return password.length() >= 8 &&
           password.matches(".*[A-Z].*") &&
           password.matches(".*[a-z].*") &&
           password.matches(".*[0-9].*");
  }

  // File I/O Methods
  private void loadUserCredentials() {
    try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
          userCredentials.put(parts[0], parts[1]);
        }
      }
    } catch (IOException e) {
      // File doesn't exist or can't be read, use default credentials
      userCredentials.put("user", "password");
      userCredentials.put("admin", "admin123");
    }
  }

  private void saveUserCredentials() {
    try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
      for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
        writer.println(entry.getKey() + ":" + entry.getValue());
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "Error saving user credentials!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // Main method
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        // Set system look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      new ManualLibrary().setVisible(true);
    });
  }
}


