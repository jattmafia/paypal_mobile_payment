# paypal_mobile_payment

Paypal biblioteca de pagamento não oficial.

> Feito por Renato Alcantara // https://www.linkedin.com/in/renato-alcantara-5011a416b/


## Antes de comçar
Essa biblioteca está em progresso, então por favor tome cuidado e qualquer problema me reporte.

## Para começar
* Crie uma conta no paypal como vendedor
* Acesse a parte de desenvolver

## Android
Só implementar e usar

## Ios
Não tenho acesso ao macbook fique avontade para contribuir.

## Instalação
adicione isso ao seu arquivo pubspec.yaml:
```yaml
dependencies:
  paypal_mobile_payment: ^0.0.1
```
adicione o seguinte importação ao código dart
```dart
import 'package:paypal_mobile_payment/paypal_mobile_payment.dart';
```

## Exemple de uso
```flutter
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:paypal_mobile_payment/paypal_mobile_payment.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
    
    // Produto vindo do seu carrinho
    var products = {
      "description": "My first purched",
      "price": "1.75",
      "currency": "USD"
    };

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              // Botão de pagamento
              FlatButton(
                  color: Colors.blueAccent,
                  onPressed: () async {
                    // Faz a chamada da classe de pagamento e recupera a resposta
                    var result = await PaypalMobilePayment.startCheckout(
                        clientId:
                            "SUA-KEY-VEM-AQUI",
                        items: products
                        );

                        // Printa a resposta
                   print(result);
                  },
                  child: Text("Payment")),,
            ],
          ),
        ),
      ),
    );
  }
}

```

## Correções
Crie uma issue https://github.com/Git-RenatoAlcantara/paypal_mobile_payment

