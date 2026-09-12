import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_provider.dart';
import '../screens/home/home_dashboard_screen.dart';
import '../screens/policy/policy_screen.dart';
import '../screens/rainfall/rainfall_detail_screen.dart';
import '../screens/payout/payout_screen.dart';
import '../screens/notifications/notification_center_screen.dart';
import 'connectivity_banner.dart';
import 'voice_action_fab.dart';

class AppShell extends StatefulWidget {
  const AppShell({super.key});
  @override
  State<AppShell> createState() => _AppShellState();
}

class _AppShellState extends State<AppShell> {
  int index = 0;
  final pages = const [
    HomeDashboardScreen(),
    PolicyScreen(),
    RainfallDetailScreen(),
    PayoutScreen(),
    NotificationCenterScreen()
  ];
  final titles = const [
    'Farm Protection',
    'Farm Protection Policy',
    'Rainfall Monitor',
    'Insurance Payout',
    'Farm Alerts'
  ];

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    return Scaffold(
      appBar: AppBar(
        title: Text(titles[index]),
        actions: [
          IconButton(
              tooltip: 'Sync farm data',
              onPressed: provider.syncing ? null : () => provider.syncPending(),
              icon: Icon(
                  provider.syncing ? Icons.sync : Icons.cloud_sync_outlined)),
          IconButton(
              tooltip: 'Settings',
              onPressed: () => Navigator.pushNamed(context, '/settings'),
              icon: const Icon(Icons.tune_rounded)),
        ],
      ),
      body: Column(children: [
        ConnectivityBanner(
            online: provider.online,
            syncing: provider.syncing,
            pending: provider.pending),
        Expanded(child: IndexedStack(index: index, children: pages))
      ]),
      floatingActionButton: const VoiceActionFab(),
      bottomNavigationBar: NavigationBar(
          selectedIndex: index,
          onDestinationSelected: (value) => setState(() => index = value),
          destinations: const [
            NavigationDestination(
                icon: Icon(Icons.home_outlined),
                selectedIcon: Icon(Icons.home),
                label: 'Home'),
            NavigationDestination(
                icon: Icon(Icons.shield_outlined),
                selectedIcon: Icon(Icons.shield),
                label: 'Policy'),
            NavigationDestination(
                icon: Icon(Icons.water_drop_outlined),
                selectedIcon: Icon(Icons.water_drop),
                label: 'Rainfall'),
            NavigationDestination(
                icon: Icon(Icons.currency_rupee_outlined),
                selectedIcon: Icon(Icons.currency_rupee),
                label: 'Payout'),
            NavigationDestination(
                icon: Icon(Icons.notifications_none_rounded),
                selectedIcon: Icon(Icons.notifications_rounded),
                label: 'Alerts'),
          ]),
    );
  }
}
