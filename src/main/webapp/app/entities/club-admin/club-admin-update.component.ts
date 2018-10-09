import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IClub_admin } from 'app/shared/model/club-admin.model';
import { Club_adminService } from './club-admin.service';
import { IUser, UserService } from 'app/core';
import { IClub } from 'app/shared/model/club.model';
import { ClubService } from 'app/entities/club';

@Component({
    selector: 'jhi-club-admin-update',
    templateUrl: './club-admin-update.component.html'
})
export class Club_adminUpdateComponent implements OnInit {
    private _club_admin: IClub_admin;
    isSaving: boolean;

    users: IUser[];

    clubs: IClub[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private club_adminService: Club_adminService,
        private userService: UserService,
        private clubService: ClubService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ club_admin }) => {
            this.club_admin = club_admin;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.club_admin.id !== undefined) {
            this.subscribeToSaveResponse(this.club_adminService.update(this.club_admin));
        } else {
            this.subscribeToSaveResponse(this.club_adminService.create(this.club_admin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClub_admin>>) {
        result.subscribe((res: HttpResponse<IClub_admin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackClubById(index: number, item: IClub) {
        return item.id;
    }
    get club_admin() {
        return this._club_admin;
    }

    set club_admin(club_admin: IClub_admin) {
        this._club_admin = club_admin;
    }
}
