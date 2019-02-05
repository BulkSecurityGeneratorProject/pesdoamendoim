import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConfrontation } from 'app/shared/model/confrontation.model';

type EntityResponseType = HttpResponse<IConfrontation>;
type EntityArrayResponseType = HttpResponse<IConfrontation[]>;

@Injectable({ providedIn: 'root' })
export class ConfrontationService {
    public resourceUrl = SERVER_API_URL + 'api/confrontations';

    constructor(protected http: HttpClient) {}

    create(confrontation: IConfrontation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(confrontation);
        return this.http
            .post<IConfrontation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(confrontation: IConfrontation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(confrontation);
        return this.http
            .put<IConfrontation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IConfrontation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConfrontation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(confrontation: IConfrontation): IConfrontation {
        const copy: IConfrontation = Object.assign({}, confrontation, {
            dataConfronto:
                confrontation.dataConfronto != null && confrontation.dataConfronto.isValid() ? confrontation.dataConfronto.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataConfronto = res.body.dataConfronto != null ? moment(res.body.dataConfronto) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((confrontation: IConfrontation) => {
                confrontation.dataConfronto = confrontation.dataConfronto != null ? moment(confrontation.dataConfronto) : null;
            });
        }
        return res;
    }
}
