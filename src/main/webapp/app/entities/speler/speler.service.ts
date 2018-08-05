import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpeler } from 'app/shared/model/speler.model';

type EntityResponseType = HttpResponse<ISpeler>;
type EntityArrayResponseType = HttpResponse<ISpeler[]>;

@Injectable({ providedIn: 'root' })
export class SpelerService {
    private resourceUrl = SERVER_API_URL + 'api/spelers';

    constructor(private http: HttpClient) {}

    create(speler: ISpeler): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(speler);
        return this.http
            .post<ISpeler>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(speler: ISpeler): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(speler);
        return this.http
            .put<ISpeler>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISpeler>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISpeler[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(speler: ISpeler): ISpeler {
        const copy: ISpeler = Object.assign({}, speler, {
            geboorteDatum: speler.geboorteDatum != null && speler.geboorteDatum.isValid() ? speler.geboorteDatum.format(DATE_FORMAT) : null,
            debuut: speler.debuut != null && speler.debuut.isValid() ? speler.debuut.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.geboorteDatum = res.body.geboorteDatum != null ? moment(res.body.geboorteDatum) : null;
        res.body.debuut = res.body.debuut != null ? moment(res.body.debuut) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((speler: ISpeler) => {
            speler.geboorteDatum = speler.geboorteDatum != null ? moment(speler.geboorteDatum) : null;
            speler.debuut = speler.debuut != null ? moment(speler.debuut) : null;
        });
        return res;
    }
}
