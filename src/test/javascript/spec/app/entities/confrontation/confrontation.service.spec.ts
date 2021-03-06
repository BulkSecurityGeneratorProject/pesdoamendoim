/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';
import { IConfrontation, Confrontation } from 'app/shared/model/confrontation.model';

describe('Service Tests', () => {
    describe('Confrontation Service', () => {
        let injector: TestBed;
        let service: ConfrontationService;
        let httpMock: HttpTestingController;
        let elemDefault: IConfrontation;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ConfrontationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Confrontation(0, currentDate, 0, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataConfronto: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Confrontation', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataConfronto: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataConfronto: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Confrontation(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Confrontation', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataConfronto: currentDate.format(DATE_TIME_FORMAT),
                        scorePlayer1: 1,
                        scorePlayer2: 1,
                        penaltyPlayer1: 1,
                        penaltyPlayer2: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataConfronto: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Confrontation', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataConfronto: currentDate.format(DATE_TIME_FORMAT),
                        scorePlayer1: 1,
                        scorePlayer2: 1,
                        penaltyPlayer1: 1,
                        penaltyPlayer2: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataConfronto: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Confrontation', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
