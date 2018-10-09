import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtcprojectSharedModule } from 'app/shared';
import { RtcprojectAdminModule } from 'app/admin/admin.module';
import {
    Club_adminComponent,
    Club_adminDetailComponent,
    Club_adminUpdateComponent,
    Club_adminDeletePopupComponent,
    Club_adminDeleteDialogComponent,
    club_adminRoute,
    club_adminPopupRoute
} from './';

const ENTITY_STATES = [...club_adminRoute, ...club_adminPopupRoute];

@NgModule({
    imports: [RtcprojectSharedModule, RtcprojectAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        Club_adminComponent,
        Club_adminDetailComponent,
        Club_adminUpdateComponent,
        Club_adminDeleteDialogComponent,
        Club_adminDeletePopupComponent
    ],
    entryComponents: [Club_adminComponent, Club_adminUpdateComponent, Club_adminDeleteDialogComponent, Club_adminDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtcprojectClub_adminModule {}
