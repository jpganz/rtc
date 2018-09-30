import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RtcprojectClubModule } from './club/club.module';
import { RtcprojectProjectModule } from './project/project.module';
import { RtcprojectMemberModule } from './member/member.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RtcprojectClubModule,
        RtcprojectProjectModule,
        RtcprojectMemberModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtcprojectEntityModule {}
