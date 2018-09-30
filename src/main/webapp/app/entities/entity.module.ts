import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RtcprojectClubModule } from './club/club.module';
import { RtcprojectProjectModule } from './project/project.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RtcprojectClubModule,
        RtcprojectProjectModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtcprojectEntityModule {}
