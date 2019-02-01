package org.jmeterplugins.repository;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.jorphan.gui.ComponentUtil;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import pe.kr.ddakker.test.examples.TextAreaOutputStream;

public class PluginManagerDialog extends JDialog implements ActionListener, ComponentListener, HyperlinkListener {
    /**
     *
     */
    private static final long serialVersionUID = 888467568782611707L;
    private static final Logger log = LoggingManager.getLoggerForClass();
    public static final Border SPACING = BorderFactory.createEmptyBorder(5, 5, 5, 5);
//    private final PluginManager manager;
//    private final JTextPane modifs = new JTextPane();

    //    private final PluginsList installed;
//    private final PluginsList available;
//    private final PluginUpgradesList upgrades;
//    private final JSplitPane topAndDown = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JLabel statusLabel = new JLabel("");
    private JEditorPane failureLabel = new JEditorPane();
    //    private JScrollPane failureScrollPane = new JScrollPane(failureLabel);
//    private final ChangeListener cbNotifier;
//    private final ChangeListener cbUpgradeNotifier;
    private JTable jTable = null;


    DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent  // 셀렌더러
        (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JCheckBox box = new JCheckBox();
            box.setSelected(((Boolean) value).booleanValue());
            box.setHorizontalAlignment(JLabel.CENTER);
            return box;
        }
    };

    public PluginManagerDialog() {
        super((JFrame) null, "JMeter Remote Agent Installer", true);
//        setLayout(new BorderLayout());
        addComponentListener(this);
//        manager = aManager;
//        Dimension size = new Dimension(1024, 768);
//        setSize(size);
//        setSize(200, 500);
//        setPreferredSize(size);
//        setSize(50, 200);
//        setLocation(MouseInfo.getPointerInfo().getLocation());


        // 상단 테이블
        String columnNames[] =
                {"No", "IP", "SSH(Port)", "ID", "Password", "Sudo"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        model.addRow(new Object[]{1, "192.168.23.2", 22, "dk", "pwd", false});

//        jTable = new JTable(rowData, columnNames);
        jTable = new JTable(model);


        JCheckBox box = new JCheckBox();
        box.setHorizontalAlignment(JLabel.CENTER);
        jTable.getColumn("Sudo").setCellRenderer(dcr);
        jTable.getColumn("Sudo").setCellEditor(new DefaultCellEditor(box));

        JScrollPane jScollPane = new JScrollPane(jTable);
        jScollPane.setPreferredSize(new Dimension(500, 200));
        add(jScollPane, BorderLayout.NORTH);


        // 중간 콘솔 창
        JTextArea ta = new JTextArea();
        TextAreaOutputStream taos = new TextAreaOutputStream(ta, 1000000);
        PrintStream ps = new PrintStream(taos);
        System.setOut(ps);
        System.setErr(ps);


        JScrollPane taScollPane = new JScrollPane(ta);

//        taScollPanel.setAutoscrolls(true);
        taScollPane.setPreferredSize(new Dimension(500, 300));
        add(taScollPane, BorderLayout.CENTER);


        // 하단 시작

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(SPACING);

        JPanel btnLeftPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        btnLeftPanel.add(btnAdd);
        JButton btnDel = new JButton("Del");
        btnLeftPanel.add(btnDel);

        btnPanel.add(btnLeftPanel, BorderLayout.WEST);
        JButton btnInstall = new JButton("Install");
        btnPanel.add(btnInstall, BorderLayout.EAST);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTableLow();
            }
        });

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delTableLow();
            }
        });

        btnInstall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnInstall();
            }
        });

        add(btnPanel, BorderLayout.PAGE_END);

//        setIconImage(PluginManagerMenuItem.getPluginsIcon(manager.hasAnyUpdates()).getImage());
        ComponentUtil.centerComponentInWindow(this);

        failureLabel.setContentType("text/html");
        failureLabel.addHyperlinkListener(this);

        /*final GenericCallback<Object> statusRefresh = new GenericCallback<Object>() {
            @Override
            public void notify(Object ignored) {
                String changeText = manager.getChangesAsText();
                modifs.setText(changeText);
                apply.setEnabled(!changeText.isEmpty() && installed.isEnabled());
            }
        };

        cbNotifier = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof PluginCheckbox) {
                    PluginCheckbox checkbox = (PluginCheckbox) e.getSource();
                    Plugin plugin = checkbox.getPlugin();
                    manager.toggleInstalled(plugin, checkbox.isSelected());
                    statusRefresh.notify(this);
                }
            }
        };

        cbUpgradeNotifier = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof PluginCheckbox) {
                    PluginCheckbox checkbox = (PluginCheckbox) e.getSource();
                    Plugin plugin = checkbox.getPlugin();
                    if (checkbox.isSelected()) {
                        plugin.setCandidateVersion(checkbox.getPlugin().getMaxVersion());
                    } else {
                        plugin.setCandidateVersion(checkbox.getPlugin().getInstalledVersion());
                    }
                    statusRefresh.notify(this);
                }
            }
        };*/

        /*installed = new PluginsList(statusRefresh);
        available = new PluginsList(statusRefresh);
        upgrades = new PluginUpgradesList(statusRefresh);*/

        /*if (manager.hasPlugins()) {
            setPlugins();
        } else {
            loadPlugins();
        }*/

//        topAndDown.setResizeWeight(.75);
//        topAndDown.setDividerSize(5);
//        topAndDown.setTopComponent(getTabsPanel());

//        topAndDown.setBottomComponent(getBottomPanel());
//        add(topAndDown, BorderLayout.CENTER);
//        add(getBottomPanel());
//        statusRefresh.notify(this); // to reflect upgrades
    }

    private void addTableLow() {
        System.out.println("addTableLow");
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();

        model.addRow(new Object[]{model.getRowCount() + 1, "192.168.23.2", 22, "dk", "pwd", false});
    }

    private void delTableLow() {
        System.out.println("delTableLow");
        int row = jTable.getSelectedRow();
        if (row > -1) {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.removeRow(row);
        }
    }

    private void btnInstall() {
        System.out.println("btnInstall");
//        MediocreExecJavac.main(null);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    /*private void setPlugins() {
        installed.setPlugins(manager.getInstalledPlugins(), cbNotifier);
        available.setPlugins(manager.getAvailablePlugins(), cbNotifier);
        upgrades.setPlugins(manager.getUpgradablePlugins(), cbUpgradeNotifier);
    }*/

    /*private void loadPlugins() {
        if (!manager.hasPlugins()) {
            try {
                manager.load();
                setPlugins();
            } catch (Throwable e) {
                log.error("Failed to load plugins manager", e);
                ByteArrayOutputStream text = new ByteArrayOutputStream(4096);
                e.printStackTrace(new PrintStream(text));
                String msg = "<p>Failed to download plugins repository.<br/>";
                msg += "One of the possible reasons is that you have proxy requirement for Internet connection.</p>" +
                        " Please read the instructions on this page: " +
                        "<a href=\"https://jmeter-plugins.org/wiki/PluginsManagerNetworkConfiguration/\">" +
                        "https://jmeter-plugins.org/wiki/PluginsManagerNetworkConfiguration/</a>" +
                        " <br><br>Error's technical details: <pre>" + text.toString() + "</pre><br>";
                failureLabel.setText("<html>" + msg + "</html>");
                failureLabel.setEditable(false);
                add(failureScrollPane, BorderLayout.CENTER);
                failureLabel.setCaretPosition(0);
            }
        }
    }*/

    /*private Component getTabsPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Installed Plugins", installed);
        *//*tabbedPane.addTab("Available Plugins", available);
        tabbedPane.addTab("Upgrades", upgrades);*//*
        return tabbedPane;
    }*/

    private JPanel getBottomPanel() {
//        apply.setEnabled(false);
//        modifs.setEditable(false);
//        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.ITALIC));

//        JPanel panel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(3, 1));
//        JPanel panel = new JPanel(new CardLayout());
//        panel.setSize(100, 100);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel labelPanel = new JPanel(new GridLayout(1, 4));
//        Dimension maximumSize = new Dimension(100,30);
//        labelPanel.setMaximumSize(maximumSize);
        Label remoteIpLabel = new Label("IP", Label.CENTER);
        labelPanel.add(remoteIpLabel);
        Label remotePortLabel = new Label("SSH(Port)", Label.CENTER);
        labelPanel.add(remotePortLabel);
        Label remoteUserIdLabel = new Label("User ID", Label.CENTER);
        labelPanel.add(remoteUserIdLabel);
        Label remoteUserPwdLabel = new Label("Password", Label.CENTER);
        labelPanel.add(remoteUserPwdLabel);
        labelPanel.setSize(100, 100);
        panel.add(labelPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(1, 4));
        final TextField remoteIp = new TextField();
        gridPanel.add(remoteIp);
        final TextField remotePort = new TextField();
        gridPanel.add(remotePort);
        final TextField userId = new TextField();
        gridPanel.add(userId);
        final TextField userPwd = new TextField();
        gridPanel.add(userPwd);

        panel.add(gridPanel, BorderLayout.CENTER);


        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBorder(SPACING);
        JButton apply = new JButton("Install");
        btnPanel.add(apply, BorderLayout.EAST);
//        btnPanel.add(statusLabel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        apply.addActionListener(this);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed()");
        /*statusLabel.setForeground(Color.BLACK);
        enableComponents(false);
        new Thread() {
            @Override
            public void run() {
                // FIXME: what to do when user presses "cancel" on save test plan dialog?
                GenericCallback<String> statusChanged = new GenericCallback<String>() {
                    @Override
                    public void notify(final String s) {
                        SwingUtilities.invokeLater(
                                new Runnable() {

                                    @Override
                                    public void run() {
                                        statusLabel.setText(s);
                                        repaint();
                                    }
                                });
                    }
                };
                try {
                    LinkedList<String> options = null;
                    String testPlan = GuiPackage.getInstance().getTestPlanFile();
                    if (testPlan != null) {
                        options = new LinkedList<>();
                        options.add("-t");
                        options.add(testPlan);
                    }
                    manager.applyChanges(statusChanged, true, options);
                    ActionRouter.getInstance().actionPerformed(new ActionEvent(this, 0, ActionNames.EXIT));
                } catch (DownloadException ex) {
                    enableComponents(true);
                    statusLabel.setForeground(Color.RED);
                    statusChanged.notify("Failed to apply changes: " + ex.getMessage());
                } catch (Exception ex) {
                    statusLabel.setForeground(Color.RED);
                    statusChanged.notify("Failed to apply changes: " + ex.getMessage());
                    throw ex;
                }
            }
        }.start();*/
    }

    private void enableComponents(boolean enable) {
        /*installed.setEnabled(enable);
        available.setEnabled(enable);
        upgrades.setEnabled(enable);*/
//        apply.setEnabled(enable);
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent evt) {
//        loadPlugins();
//        topAndDown.setVisible(!manager.allPlugins.isEmpty());
//        failureLabel.setVisible(manager.allPlugins.isEmpty());
        pack();
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            PluginsList.openInBrowser(e.getURL().toString());
        }
    }
}
