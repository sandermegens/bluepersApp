import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWedstrijd } from 'app/shared/model/wedstrijd.model';

type EntityResponseType = HttpResponse<IWedstrijd>;
type EntityArrayResponseType = HttpResponse<IWedstrijd[]>;

@Injectable({ providedIn: 'root' })
export class WedstrijdService {
    private resourceUrl = SERVER_API_URL + 'api/wedstrijds';

    constructor(private http: HttpClient) {}

    create(wedstrijd: IWedstrijd): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wedstrijd);
        return this.http
            .post<IWedstrijd>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(wedstrijd: IWedstrijd): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(wedstrijd);
        return this.http
            .put<IWedstrijd>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWedstrijd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWedstrijd[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(wedstrijd: IWedstrijd): IWedstrijd {
        const copy: IWedstrijd = Object.assign({}, wedstrijd, {
            datum: wedstrijd.datum != null && wedstrijd.datum.isValid() ? wedstrijd.datum.format(DATE_FORMAT) : null,
            tijd: wedstrijd.tijd != null && wedstrijd.tijd.isValid() ? wedstrijd.tijd.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.datum = res.body.datum != null ? moment(res.body.datum) : null;
        res.body.tijd = res.body.tijd != null ? moment(res.body.tijd) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((wedstrijd: IWedstrijd) => {
            wedstrijd.datum = wedstrijd.datum != null ? moment(wedstrijd.datum) : null;
            wedstrijd.tijd = wedstrijd.tijd != null ? moment(wedstrijd.tijd) : null;
        });
        return res;
    }
}
