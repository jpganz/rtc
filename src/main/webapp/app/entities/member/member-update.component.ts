import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMember } from 'app/shared/model/member.model';
import { MemberService } from './member.service';
import { IClub } from 'app/shared/model/club.model';
import { ClubService } from 'app/entities/club';

@Component({
    selector: 'jhi-member-update',
    templateUrl: './member-update.component.html'
})
export class MemberUpdateComponent implements OnInit {
    private _member: IMember;
    isSaving: boolean;

    clubs: IClub[];
    birthdateDp: any;
    starting_membershipDp: any;
    ending_membershipDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private memberService: MemberService,
        private clubService: ClubService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ member }) => {
            this.member = member;
        });
        this.clubService.query().subscribe(
            (res: HttpResponse<IClub[]>) => {
                this.clubs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.member.id !== undefined) {
            this.subscribeToSaveResponse(this.memberService.update(this.member));
        } else {
            this.subscribeToSaveResponse(this.memberService.create(this.member));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>) {
        result.subscribe((res: HttpResponse<IMember>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClubById(index: number, item: IClub) {
        return item.id;
    }
    get member() {
        return this._member;
    }

    set member(member: IMember) {
        this._member = member;
    }
}
