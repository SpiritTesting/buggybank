import {RestService} from "./rest.service";
import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ACCOUNTS, USERS} from "../server/db-data";
import {Konto} from "./kontouebersicht/konto.model";
import {HttpErrorResponse} from '@angular/common/http';
import truthy = jasmine.truthy;

// DEFINE WHAT SERVICE WILL BE TESTED
describe('RestService', () => {


// DEFINE VARIABLEN ( ACTUAL SERVICE AND ITS DEPENDENCIES )
  let restService: RestService,
    // DEFINE HTTP TESTING CONTROLLER WHICH IS PART OF HTTP CLIENT TESTING MODULE FROM ANGULAR CORE TO CREATE TEST DATA
    httpTestingController: HttpTestingController;


// DO DEPENDENCY INJECTIONS AND INITIALISE VARIABLES
  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        // IMPORT HTTP CLIENT TESTING MODULE
        HttpClientTestingModule
      ],
      providers: [
        // GET INSTANCE OF OUR SERVICE
        RestService
      ]
    });

    // INITIALISE SERVICE
    restService = TestBed.inject(RestService);
    // INITIALISE HTTP TESTING CONTROLLER FROM ANGULAR CORE TESTING MODULE
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

  fit('should find an user by user number', () => {

    restService.getKonto('12340003')
      .subscribe(payload => {
        expect(payload).toBeTruthy();
        expect(payload.name).toBe('First account');

      });

    const req = httpTestingController.expectOne(restService.kontenUrl + '12340003');

    expect(req.request.method).toEqual("GET");
    req.flush(ACCOUNTS[1]);

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


  it('should save new account', () => {


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

  // CHECK REQUEST URL; CHECK IF THE NAME IG GOING TO BE UPDATED
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


  afterEach(() => {
    httpTestingController.verify();
  });

});















