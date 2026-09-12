import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';

class RegistrationScreen extends StatefulWidget {
  const RegistrationScreen({super.key});

  @override
  State<RegistrationScreen> createState() => _RegistrationScreenState();
}

class _RegistrationScreenState extends State<RegistrationScreen> {
  final name = TextEditingController();
  final phone = TextEditingController();
  final district = TextEditingController(text: 'Anantapur');
  final village = TextEditingController();
  String language = 'te-IN';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Create your profile')),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.fromLTRB(20, 10, 20, 28),
          children: [
            _introCard(),
            const SizedBox(height: 26),
            const Text('Farmer details',
                style: TextStyle(fontSize: 22, fontWeight: FontWeight.w900)),
            const SizedBox(height: 6),
            const Text('Use the name and place you are comfortable with.',
                style: TextStyle(color: AppColors.muted)),
            const SizedBox(height: 18),
            TextField(
                controller: name,
                textInputAction: TextInputAction.next,
                decoration: const InputDecoration(
                    labelText: 'Farmer name',
                    prefixIcon: Icon(Icons.person_outline))),
            const SizedBox(height: 12),
            TextField(
                controller: phone,
                keyboardType: TextInputType.phone,
                textInputAction: TextInputAction.next,
                decoration: const InputDecoration(
                    labelText: 'Mobile number',
                    prefixIcon: Icon(Icons.phone_outlined))),
            const SizedBox(height: 12),
            TextField(
                controller: district,
                textInputAction: TextInputAction.next,
                decoration: const InputDecoration(
                    labelText: 'District',
                    prefixIcon: Icon(Icons.location_on_outlined))),
            const SizedBox(height: 12),
            TextField(
                controller: village,
                decoration: const InputDecoration(
                    labelText: 'Village',
                    prefixIcon: Icon(Icons.home_work_outlined))),
            const SizedBox(height: 12),
            DropdownButtonFormField<String>(
              initialValue: language,
              decoration: const InputDecoration(
                  labelText: 'Preferred voice language',
                  prefixIcon: Icon(Icons.record_voice_over_outlined)),
              items: const [
                DropdownMenuItem(value: 'te-IN', child: Text('Telugu')),
                DropdownMenuItem(value: 'hi-IN', child: Text('Hindi')),
                DropdownMenuItem(value: 'en-IN', child: Text('English')),
              ],
              onChanged: (value) => setState(() => language = value!),
            ),
            const SizedBox(height: 26),
            FilledButton.icon(
              onPressed: () async {
                if (name.text.trim().isEmpty) return;
                final navigator = Navigator.of(context);
                await context.read<AppProvider>().registerFarmer(
                      name: name.text.trim(),
                      phone: phone.text.trim(),
                      district: district.text.trim(),
                      village: village.text.trim(),
                      language: language,
                    );
                if (mounted) {
                  navigator.pushReplacementNamed('/app');
                }
              },
              icon: const Icon(Icons.shield_outlined),
              label: const Text('Create Farmer Profile'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _introCard() {
    return Container(
      padding: const EdgeInsets.all(18),
      decoration: BoxDecoration(
          color: AppColors.greenSoft, borderRadius: BorderRadius.circular(22)),
      child: Row(
        children: [
          Container(
              width: 58,
              height: 58,
              decoration: const BoxDecoration(
                  color: AppColors.greenDark, shape: BoxShape.circle),
              child: const Icon(Icons.agriculture_rounded,
                  color: Colors.white, size: 31)),
          const SizedBox(width: 14),
          const Expanded(
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              Text('Your farm, protected',
                  style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.w900,
                      color: AppColors.greenDark)),
              SizedBox(height: 4),
              Text(
                  'Tell us a few details. Your profile can be saved even when you are offline.',
                  style: TextStyle(color: AppColors.muted)),
            ]),
          ),
        ],
      ),
    );
  }
}
