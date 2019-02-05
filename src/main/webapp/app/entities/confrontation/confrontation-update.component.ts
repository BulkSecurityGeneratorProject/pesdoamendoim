import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IConfrontation } from 'app/shared/model/confrontation.model';
import { ConfrontationService } from './confrontation.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';
import { IRound } from 'app/shared/model/round.model';
import { RoundService } from 'app/entities/round';

@Component({
    selector: 'jhi-confrontation-update',
    templateUrl: './confrontation-update.component.html'
})
export class ConfrontationUpdateComponent implements OnInit {
    confrontation: IConfrontation;
    isSaving: boolean;

    player1s: IPlayer[];

    player2s: IPlayer[];

    rounds: IRound[];
    dataConfronto: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected confrontationService: ConfrontationService,
        protected playerService: PlayerService,
        protected roundService: RoundService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ confrontation }) => {
            this.confrontation = confrontation;
            this.dataConfronto =
                this.confrontation.dataConfronto != null ? this.confrontation.dataConfronto.format(DATE_TIME_FORMAT) : null;
        });
        this.playerService
            .query({ filter: 'confrontation-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPlayer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPlayer[]>) => response.body)
            )
            .subscribe(
                (res: IPlayer[]) => {
                    if (!this.confrontation.player1Id) {
                        this.player1s = res;
                    } else {
                        this.playerService
                            .find(this.confrontation.player1Id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPlayer>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPlayer>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPlayer) => (this.player1s = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.playerService
            .query({ filter: 'confrontation-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPlayer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPlayer[]>) => response.body)
            )
            .subscribe(
                (res: IPlayer[]) => {
                    if (!this.confrontation.player2Id) {
                        this.player2s = res;
                    } else {
                        this.playerService
                            .find(this.confrontation.player2Id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPlayer>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPlayer>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPlayer) => (this.player2s = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.roundService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRound[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRound[]>) => response.body)
            )
            .subscribe((res: IRound[]) => (this.rounds = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.confrontation.dataConfronto = this.dataConfronto != null ? moment(this.dataConfronto, DATE_TIME_FORMAT) : null;
        if (this.confrontation.id !== undefined) {
            this.subscribeToSaveResponse(this.confrontationService.update(this.confrontation));
        } else {
            this.subscribeToSaveResponse(this.confrontationService.create(this.confrontation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfrontation>>) {
        result.subscribe((res: HttpResponse<IConfrontation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }

    trackRoundById(index: number, item: IRound) {
        return item.id;
    }
}
