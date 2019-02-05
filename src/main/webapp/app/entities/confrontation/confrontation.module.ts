import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeSdoAmendoimSharedModule } from 'app/shared';
import {
    ConfrontationComponent,
    ConfrontationDetailComponent,
    ConfrontationUpdateComponent,
    ConfrontationDeletePopupComponent,
    ConfrontationDeleteDialogComponent,
    confrontationRoute,
    confrontationPopupRoute
} from './';

const ENTITY_STATES = [...confrontationRoute, ...confrontationPopupRoute];

@NgModule({
    imports: [PeSdoAmendoimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConfrontationComponent,
        ConfrontationDetailComponent,
        ConfrontationUpdateComponent,
        ConfrontationDeleteDialogComponent,
        ConfrontationDeletePopupComponent
    ],
    entryComponents: [
        ConfrontationComponent,
        ConfrontationUpdateComponent,
        ConfrontationDeleteDialogComponent,
        ConfrontationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeSdoAmendoimConfrontationModule {}
