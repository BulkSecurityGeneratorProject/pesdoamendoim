import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeSdoAmendoimSharedModule } from 'app/shared';
import {
    CompetitionComponent,
    CompetitionDetailComponent,
    CompetitionUpdateComponent,
    CompetitionDeletePopupComponent,
    CompetitionDeleteDialogComponent,
    competitionRoute,
    competitionPopupRoute
} from './';

const ENTITY_STATES = [...competitionRoute, ...competitionPopupRoute];

@NgModule({
    imports: [PeSdoAmendoimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompetitionComponent,
        CompetitionDetailComponent,
        CompetitionUpdateComponent,
        CompetitionDeleteDialogComponent,
        CompetitionDeletePopupComponent
    ],
    entryComponents: [CompetitionComponent, CompetitionUpdateComponent, CompetitionDeleteDialogComponent, CompetitionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeSdoAmendoimCompetitionModule {}
