import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'competition',
                loadChildren: './competition/competition.module#PeSdoAmendoimCompetitionModule'
            },
            {
                path: 'groupe',
                loadChildren: './groupe/groupe.module#PeSdoAmendoimGroupeModule'
            },
            {
                path: 'round',
                loadChildren: './round/round.module#PeSdoAmendoimRoundModule'
            },
            {
                path: 'confrontation',
                loadChildren: './confrontation/confrontation.module#PeSdoAmendoimConfrontationModule'
            },
            {
                path: 'player',
                loadChildren: './player/player.module#PeSdoAmendoimPlayerModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PeSdoAmendoimEntityModule {}
