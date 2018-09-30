import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtcprojectSharedModule } from 'app/shared';
import {
    ClubComponent,
    ClubDetailComponent,
    ClubUpdateComponent,
    ClubDeletePopupComponent,
    ClubDeleteDialogComponent,
    clubRoute,
    clubPopupRoute
} from './';

const ENTITY_STATES = [...clubRoute, ...clubPopupRoute];

@NgModule({
    imports: [RtcprojectSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ClubComponent, ClubDetailComponent, ClubUpdateComponent, ClubDeleteDialogComponent, ClubDeletePopupComponent],
    entryComponents: [ClubComponent, ClubUpdateComponent, ClubDeleteDialogComponent, ClubDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtcprojectClubModule {}
