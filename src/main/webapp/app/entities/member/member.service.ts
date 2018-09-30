import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMember } from 'app/shared/model/member.model';

type EntityResponseType = HttpResponse<IMember>;
type EntityArrayResponseType = HttpResponse<IMember[]>;

@Injectable({ providedIn: 'root' })
export class MemberService {
    private resourceUrl = SERVER_API_URL + 'api/members';

    constructor(private http: HttpClient) {}

    create(member: IMember): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(member);
        return this.http
            .post<IMember>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(member: IMember): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(member);
        return this.http
            .put<IMember>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMember[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(member: IMember): IMember {
        const copy: IMember = Object.assign({}, member, {
            birthdate: member.birthdate != null && member.birthdate.isValid() ? member.birthdate.format(DATE_FORMAT) : null,
            starting_membership:
                member.starting_membership != null && member.starting_membership.isValid()
                    ? member.starting_membership.format(DATE_FORMAT)
                    : null,
            ending_membership:
                member.ending_membership != null && member.ending_membership.isValid() ? member.ending_membership.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.birthdate = res.body.birthdate != null ? moment(res.body.birthdate) : null;
        res.body.starting_membership = res.body.starting_membership != null ? moment(res.body.starting_membership) : null;
        res.body.ending_membership = res.body.ending_membership != null ? moment(res.body.ending_membership) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((member: IMember) => {
            member.birthdate = member.birthdate != null ? moment(member.birthdate) : null;
            member.starting_membership = member.starting_membership != null ? moment(member.starting_membership) : null;
            member.ending_membership = member.ending_membership != null ? moment(member.ending_membership) : null;
        });
        return res;
    }
}
