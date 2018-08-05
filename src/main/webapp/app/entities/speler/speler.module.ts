import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BluepersAppSharedModule } from 'app/shared';
import {
    SpelerComponent,
    SpelerDetailComponent,
    SpelerUpdateComponent,
    SpelerDeletePopupComponent,
    SpelerDeleteDialogComponent,
    spelerRoute,
    spelerPopupRoute
} from './';

const ENTITY_STATES = [...spelerRoute, ...spelerPopupRoute];

@NgModule({
    imports: [BluepersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SpelerComponent, SpelerDetailComponent, SpelerUpdateComponent, SpelerDeleteDialogComponent, SpelerDeletePopupComponent],
    entryComponents: [SpelerComponent, SpelerUpdateComponent, SpelerDeleteDialogComponent, SpelerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BluepersAppSpelerModule {}
