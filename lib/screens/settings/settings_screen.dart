import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({super.key});
  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  late TextEditingController url;

  @override
  void initState() {
    super.initState();
    url = TextEditingController(text: context.read<AppProvider>().api.baseUrl);
  }

  @override
  void dispose() {
    url.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    return Scaffold(
        appBar: AppBar(title: const Text('Settings')),
        body: SafeArea(
            child: ListView(
                padding: const EdgeInsets.fromLTRB(16, 10, 16, 30),
                children: [
              const Text('Make the app work for you',
                  style: TextStyle(fontSize: 25, fontWeight: FontWeight.w900)),
              const SizedBox(height: 5),
              const Text('Choose your language, connection, and demo view.',
                  style: TextStyle(color: AppColors.muted)),
              const SizedBox(height: 22),
              _sectionLabel('APP SETTINGS'),
              Card(
                  child: Column(children: [
                ListTile(
                    leading: _icon(
                        Icons.record_voice_over_outlined, AppColors.green),
                    title: const Text('Voice language',
                        style: TextStyle(fontWeight: FontWeight.w900)),
                    subtitle:
                        const Text('Hear updates in a familiar language')),
                Padding(
                    padding: const EdgeInsets.fromLTRB(16, 0, 16, 14),
                    child: DropdownButtonFormField<String>(
                        initialValue: provider.selectedLanguage,
                        items: const [
                          DropdownMenuItem(
                              value: 'te-IN', child: Text('Telugu')),
                          DropdownMenuItem(
                              value: 'hi-IN', child: Text('Hindi')),
                          DropdownMenuItem(
                              value: 'en-IN', child: Text('English'))
                        ],
                        onChanged: (value) {
                          if (value != null) provider.setLanguage(value);
                        }))
              ])),
              const SizedBox(height: 18),
              _sectionLabel('CONNECTION'),
              Card(
                  child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(children: [
                        Row(children: [
                          Icon(
                              provider.online
                                  ? Icons.cloud_done_outlined
                                  : Icons.cloud_off_outlined,
                              color: provider.online
                                  ? AppColors.green
                                  : AppColors.red,
                              size: 28),
                          const SizedBox(width: 12),
                          Expanded(
                              child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                Text(
                                    provider.online
                                        ? 'Connected'
                                        : 'Offline Mode',
                                    style: const TextStyle(
                                        fontWeight: FontWeight.w900,
                                        fontSize: 16)),
                                Text(
                                    provider.online
                                        ? 'Your latest information is available.'
                                        : 'Your saved farm information is still available.',
                                    style: const TextStyle(
                                        color: AppColors.muted, fontSize: 12))
                              ]))
                        ]),
                        const SizedBox(height: 14),
                        TextField(
                            controller: url,
                            decoration: const InputDecoration(
                                labelText: 'Spring Boot server address',
                                prefixIcon: Icon(Icons.link_rounded)),
                            onChanged: provider.setBaseUrl),
                        const SizedBox(height: 8),
                        const Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                                'Emulator: http://10.0.2.2:8080  |  Phone: use your computer LAN IP.',
                                style: TextStyle(
                                    fontSize: 11, color: AppColors.muted))),
                        const SizedBox(height: 14),
                        OutlinedButton.icon(
                            onPressed: provider.syncing
                                ? null
                                : () => provider.syncPending(),
                            icon: Icon(provider.syncing
                                ? Icons.sync
                                : Icons.cloud_sync_outlined),
                            label: const Text('Sync Farm Data'))
                      ]))),
              const SizedBox(height: 18),
              _sectionLabel('DEMO SCENARIOS'),
              Card(
                  child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text('Try each protection story',
                                style: TextStyle(
                                    fontWeight: FontWeight.w900, fontSize: 16)),
                            const SizedBox(height: 10),
                            DropdownButtonFormField<String>(
                                initialValue: provider.demoMode,
                                items: const [
                                  DropdownMenuItem(
                                      value: 'NORMAL',
                                      child:
                                          Text('Normal rainfall - Monitoring')),
                                  DropdownMenuItem(
                                      value: 'TRIGGER',
                                      child: Text(
                                          'Deficit rainfall - Trigger detected')),
                                  DropdownMenuItem(
                                      value: 'OFFLINE',
                                      child: Text('Offline Mode - Saved data'))
                                ],
                                onChanged: (value) {
                                  if (value != null)
                                    provider.setDemoMode(value);
                                })
                          ]))),
              const SizedBox(height: 18),
              Card(
                  color: AppColors.goldSoft,
                  child: const Padding(
                      padding: EdgeInsets.all(16),
                      child: Row(children: [
                        Icon(Icons.lightbulb_outline_rounded,
                            color: AppColors.amber),
                        SizedBox(width: 10),
                        Expanded(
                            child: Text(
                                'Your farm information stays useful even when the network does not.',
                                style: TextStyle(
                                    color: AppColors.ink,
                                    fontWeight: FontWeight.w700)))
                      ]))),
            ])));
  }

  Widget _sectionLabel(String text) => Padding(
      padding: const EdgeInsets.only(left: 3, bottom: 8),
      child: Text(text,
          style: const TextStyle(
              color: AppColors.greenDark,
              fontSize: 11,
              fontWeight: FontWeight.w900,
              letterSpacing: 1.1)));
  Widget _icon(IconData icon, Color color) => Container(
      width: 42,
      height: 42,
      decoration: BoxDecoration(
          color: color.withValues(alpha: .1),
          borderRadius: BorderRadius.circular(12)),
      child: Icon(icon, color: color));
}
