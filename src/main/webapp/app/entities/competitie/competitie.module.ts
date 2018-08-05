import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BluepersAppSharedModule } from 'app/shared';
import {
    CompetitieComponent,
    CompetitieDetailComponent,
    CompetitieUpdateComponent,
    CompetitieDeletePopupComponent,
    CompetitieDeleteDialogComponent,
    competitieRoute,
    competitiePopupRoute
} from './';

const ENTITY_STATES = [...competitieRoute, ...competitiePopupRoute];

@NgModule({
    imports: [BluepersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompetitieComponent,
        CompetitieDetailComponent,
        CompetitieUpdateComponent,
        CompetitieDeleteDialogComponent,
        CompetitieDeletePopupComponent
    ],
    entryComponents: [CompetitieComponent, CompetitieUpdateComponent, CompetitieDeleteDialogComponent, CompetitieDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BluepersAppCompetitieModule {}
