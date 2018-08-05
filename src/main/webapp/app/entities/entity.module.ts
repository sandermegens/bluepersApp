import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BluepersAppSpelerModule } from './speler/speler.module';
import { BluepersAppWedstrijdModule } from './wedstrijd/wedstrijd.module';
import { BluepersAppCompetitieModule } from './competitie/competitie.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BluepersAppSpelerModule,
        BluepersAppWedstrijdModule,
        BluepersAppCompetitieModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BluepersAppEntityModule {}
