import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClub_admin } from 'app/shared/model/club-admin.model';

@Component({
    selector: 'jhi-club-admin-detail',
    templateUrl: './club-admin-detail.component.html'
})
export class Club_adminDetailComponent implements OnInit {
    club_admin: IClub_admin;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ club_admin }) => {
            this.club_admin = club_admin;
        });
    }

    previousState() {
        window.history.back();
    }
}
