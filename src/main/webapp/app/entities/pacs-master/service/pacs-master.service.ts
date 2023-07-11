import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPacsMaster, NewPacsMaster } from '../pacs-master.model';

export type PartialUpdatePacsMaster = Partial<IPacsMaster> & Pick<IPacsMaster, 'id'>;

export type EntityResponseType = HttpResponse<IPacsMaster>;
export type EntityArrayResponseType = HttpResponse<IPacsMaster[]>;

@Injectable({ providedIn: 'root' })
export class PacsMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pacs-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pacsMaster: NewPacsMaster): Observable<EntityResponseType> {
    return this.http.post<IPacsMaster>(this.resourceUrl, pacsMaster, { observe: 'response' });
  }

  update(pacsMaster: IPacsMaster): Observable<EntityResponseType> {
    return this.http.put<IPacsMaster>(`${this.resourceUrl}/${this.getPacsMasterIdentifier(pacsMaster)}`, pacsMaster, {
      observe: 'response',
    });
  }

  partialUpdate(pacsMaster: PartialUpdatePacsMaster): Observable<EntityResponseType> {
    return this.http.patch<IPacsMaster>(`${this.resourceUrl}/${this.getPacsMasterIdentifier(pacsMaster)}`, pacsMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPacsMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPacsMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPacsMasterIdentifier(pacsMaster: Pick<IPacsMaster, 'id'>): number {
    return pacsMaster.id;
  }

  comparePacsMaster(o1: Pick<IPacsMaster, 'id'> | null, o2: Pick<IPacsMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getPacsMasterIdentifier(o1) === this.getPacsMasterIdentifier(o2) : o1 === o2;
  }

  addPacsMasterToCollectionIfMissing<Type extends Pick<IPacsMaster, 'id'>>(
    pacsMasterCollection: Type[],
    ...pacsMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pacsMasters: Type[] = pacsMastersToCheck.filter(isPresent);
    if (pacsMasters.length > 0) {
      const pacsMasterCollectionIdentifiers = pacsMasterCollection.map(pacsMasterItem => this.getPacsMasterIdentifier(pacsMasterItem)!);
      const pacsMastersToAdd = pacsMasters.filter(pacsMasterItem => {
        const pacsMasterIdentifier = this.getPacsMasterIdentifier(pacsMasterItem);
        if (pacsMasterCollectionIdentifiers.includes(pacsMasterIdentifier)) {
          return false;
        }
        pacsMasterCollectionIdentifiers.push(pacsMasterIdentifier);
        return true;
      });
      return [...pacsMastersToAdd, ...pacsMasterCollection];
    }
    return pacsMasterCollection;
  }
}
