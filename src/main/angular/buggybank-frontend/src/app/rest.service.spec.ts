import {RestService} from "./rest.service";
import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ACCOUNTS, USERS} from "../server/db-data";
import {Konto} from "./kontouebersicht/konto.model";

describe('RestService', () => {


  let restService: RestService,
    httpTestingController: HttpTestingController;


  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        RestService
      ]
    });

    restService = TestBed.inject(RestService);
    httpTestingController = TestBed.inject(HttpTestingController);

  });


  it('should return all users',  async() => {

    restService.getKunden()
      .subscribe(payload => {

        expect(payload).toBeTruthy('No users returned');

        const res = Object.entries(payload).map(([name, obj]) => ({ name, ...obj }))

        expect(res.length).toBeGreaterThan(0, "incorrect number of users");

        const accountsInArray = res[0];

        expect(accountsInArray[0].name).toBe(
          "Karo");

        expect(accountsInArray[0].kundennummer).toBe(
          "KDNR0003");

      });

    const req = httpTestingController.expectOne(restService.kundenUrl);

    expect(req.request.method).toEqual("GET");

    req.flush(
      {
        payload: Object.values(USERS)
      }
    );

  });


  it('should find an user by user number',  async() =>{

    restService.getKunde('KDNR0003')
      .subscribe(payload => {
        expect(payload).toBeTruthy();
        console.log(payload);
        expect(payload.name).toBe('Karo');
        expect(payload.konten.length).toEqual(3)
        expect(payload.saldo).toBe("EUR 110.00")

      });



    const req = httpTestingController.expectOne(restService.kundenUrl + 'KDNR0003');

    expect(req.request.method).toEqual("GET");
    req.flush(USERS[1]);

  });


  it('should return all accounts',  async() => {

    restService.getKonten()
      .subscribe(payload => {

        expect(payload).toBeTruthy('No accounts returned');

        const res = Object.entries(payload).map(([name, obj]) => ({ name, ...obj }))

        expect(res.length).toBeGreaterThan(0, "incorrect number of accounts");

        const accountsInArray = res[0];

        expect(accountsInArray[0].name).toBe(
          "First account");

        expect(accountsInArray[0].kontonummer).toBe(
          "12340003");

      });

    const req = httpTestingController.expectOne(restService.kontenUrl);

    expect(req.request.method).toEqual("GET");

    req.flush(
      {
        payload: Object.values(ACCOUNTS)
      }
    );

  });


  it('should find an account by account number', () => {

    restService.getKonto('12340003')
      .subscribe(payload => {
        expect(payload).toBeTruthy();
        expect(payload.name).toBe('First account');

      });

    const req = httpTestingController.expectOne(restService.kontenUrl + '12340003');

    expect(req.request.method).toEqual("GET");
    req.flush(ACCOUNTS[1]);

  });


  it('should save  account', () => {


    restService.postKonto('KDNR0003')
      .subscribe(response => {
        console.log(response.body);
        expect(response.body).toBeTruthy();
      });


    const req = httpTestingController.expectOne(restService.kontenUrl);
    console.log(req);
    expect(req.request.method).toEqual("POST");

    expect(req.request.body.kundennummer)
      .toEqual('KDNR0003');


    req.flush({
      ...ACCOUNTS[1],
    })

  });


  it('should update an account', () => {

    const changes :Konto =
      {
        kontonummer: "123400031",
        betrag: "EUR 10.00",
        name: 'New name for account'
      };
    // test if the changes sent to BE updated the account
    restService.putKonto(changes.kontonummer, changes)
      .subscribe(course => {
        console.log(JSON.stringify(course) + 'course');
        expect(course.kontonummer).toEqual(changes.kontonummer);
        expect(course.betrag).toEqual(changes.betrag);
        expect(course.name).toEqual(changes.name);
      });

    // test if the changes were sent as request to BE
    const req = httpTestingController.expectOne(restService.kontenUrl + changes.kontonummer);
    console.log(req);
    expect(req.request.method).toEqual("PUT");

    expect(req.request.body.kontonummer)
      .toEqual(changes.kontonummer);

    expect(req.request.body.name)
      .toEqual(changes.name);

    expect(req.request.body.betrag)
      .toEqual(changes.betrag);


    req.flush({
      ...ACCOUNTS[1],
      ...changes
    })

  });


  it('should save new user', () => {


    restService.postKunde('New User')
      .subscribe(response => {
        expect(response.body).toBeTruthy();
      });


    const req = httpTestingController.expectOne(restService.kundenUrl);

    expect(req.request.method).toEqual("POST");

    expect(req.request.body.name)
      .toEqual('New User');

    req.flush({
      ...ACCOUNTS[1],
    })

  });


  fit('should save new payment', () => {

    const changes :any =
      {
        quelle: 'User1',
        ziel: 'User2',
        betrag: '50 EUR',
        zweck: 'Test payment'
      };
    // test if the changes sent to BE updated the account
    restService.postZahlung(changes.quelle, changes.ziel , changes.betrag, changes.zweck)
      .subscribe(course => {
        const payment: any = course.body;
        expect(payment.betrag).toEqual(req.request.body.betrag);
        expect(payment.zweck).toEqual(req.request.body.zweck);
      });

    // test if the changes were sent as request to BE
    const req = httpTestingController.expectOne(restService.kontenUrl + changes.quelle + "/" + changes.ziel);
    console.log(req);
    expect(req.request.method).toEqual("POST");

       expect(req.request.body.betrag)
      .toEqual(changes.betrag);

      expect(req.request.body.zweck)
      .toEqual(changes.zweck);



    req.flush({
      ...changes
    })

  });

});















