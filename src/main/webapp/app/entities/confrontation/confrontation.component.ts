import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConfrontation } from 'app/shared/model/confrontation.model';
import { AccountService } from 'app/core';
import { ConfrontationService } from './confrontation.service';

@Component({
    selector: 'jhi-confrontation',
    templateUrl: './confrontation.component.html'
})
export class ConfrontationComponent implements OnInit, OnDestroy {
    confrontations: IConfrontation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected confrontationService: ConfrontationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.confrontationService
            .query()
            .pipe(
                filter((res: HttpResponse<IConfrontation[]>) => res.ok),
                map((res: HttpResponse<IConfrontation[]>) => res.body)
            )
            .subscribe(
                (res: IConfrontation[]) => {
                    this.confrontations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConfrontations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConfrontation) {
        return item.id;
    }

    registerChangeInConfrontations() {
        this.eventSubscriber = this.eventManager.subscribe('confrontationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
