import {Konto} from "../app/kontouebersicht/konto.model";

export const ACCOUNTS:any = {
1:{
  kontonummer: "12340003",
  betrag: "EUR 0.00",
  name: 'First account'
},

2:{
  kontonummer: "123400031",
  betrag: "EUR 10.00",
  name: null
},


3:{
  kontonummer: "1234000311",
  betrag: "EUR 110.00",
  name: null
},
}

export const USERS:any = {
  1:{
    kundennummer: "KDNR0003",
    name: "Karo",
    konten: [
          {
        kontonummer: "12340003",
        betrag: "EUR 0.00",
        name: 'First account'
    },

    {
        kontonummer: "123400031",
        betrag: "EUR 10.00",
        name: null
    },

    {
        kontonummer: "1234000311",
        betrag: "EUR 110.00",
        name: null
    },
    ],
    saldo: "EUR 110.00"
  }
}


export function setupAccounts() : Konto[] {
  return Object.values(ACCOUNTS) as Konto[];
}



