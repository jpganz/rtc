import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClub } from 'app/shared/model/club.model';

type EntityResponseType = HttpResponse<IClub>;
type EntityArrayResponseType = HttpResponse<IClub[]>;

@Injectable({ providedIn: 'root' })
export class ClubService {
    private resourceUrl = SERVER_API_URL + 'api/clubs';

    constructor(private http: HttpClient) {}

    create(club: IClub): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(club);
        return this.http
            .post<IClub>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(club: IClub): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(club);
        return this.http
            .put<IClub>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClub>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClub[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(club: IClub): IClub {
        const copy: IClub = Object.assign({}, club, {
            creation_date: club.creation_date != null && club.creation_date.isValid() ? club.creation_date.format(DATE_FORMAT) : null,
            certification_date:
                club.certification_date != null && club.certification_date.isValid() ? club.certification_date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creation_date = res.body.creation_date != null ? moment(res.body.creation_date) : null;
        res.body.certification_date = res.body.certification_date != null ? moment(res.body.certification_date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((club: IClub) => {
            club.creation_date = club.creation_date != null ? moment(club.creation_date) : null;
            club.certification_date = club.certification_date != null ? moment(club.certification_date) : null;
        });
        return res;
    }
}
