import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BluepersAppSharedModule } from 'app/shared';
import {
    WedstrijdComponent,
    WedstrijdDetailComponent,
    WedstrijdUpdateComponent,
    WedstrijdDeletePopupComponent,
    WedstrijdDeleteDialogComponent,
    wedstrijdRoute,
    wedstrijdPopupRoute
} from './';

const ENTITY_STATES = [...wedstrijdRoute, ...wedstrijdPopupRoute];

@NgModule({
    imports: [BluepersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WedstrijdComponent,
        WedstrijdDetailComponent,
        WedstrijdUpdateComponent,
        WedstrijdDeleteDialogComponent,
        WedstrijdDeletePopupComponent
    ],
    entryComponents: [WedstrijdComponent, WedstrijdUpdateComponent, WedstrijdDeleteDialogComponent, WedstrijdDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BluepersAppWedstrijdModule {}
