import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeSdoAmendoimSharedModule } from 'app/shared';
import {
    RoundComponent,
    RoundDetailComponent,
    RoundUpdateComponent,
    RoundDeletePopupComponent,
    RoundDeleteDialogComponent,
    roundRoute,
    roundPopupRoute
} from './';

const ENTITY_STATES = [...roundRoute, ...roundPopupRoute];

@NgModule({
    imports: [PeSdoAmendoimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RoundComponent, RoundDetailComponent, RoundUpdateComponent, RoundDeleteDialogComponent, RoundDeletePopupComponent],
    entryComponents: [RoundComponent, RoundUpdateComponent, RoundDeleteDialogComponent, RoundDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeSdoAmendoimRoundModule {}
