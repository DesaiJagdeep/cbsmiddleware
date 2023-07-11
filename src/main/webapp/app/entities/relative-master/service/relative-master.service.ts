import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRelativeMaster, NewRelativeMaster } from '../relative-master.model';

export type PartialUpdateRelativeMaster = Partial<IRelativeMaster> & Pick<IRelativeMaster, 'id'>;

export type EntityResponseType = HttpResponse<IRelativeMaster>;
export type EntityArrayResponseType = HttpResponse<IRelativeMaster[]>;

@Injectable({ providedIn: 'root' })
export class RelativeMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/relative-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(relativeMaster: NewRelativeMaster): Observable<EntityResponseType> {
    return this.http.post<IRelativeMaster>(this.resourceUrl, relativeMaster, { observe: 'response' });
  }

  update(relativeMaster: IRelativeMaster): Observable<EntityResponseType> {
    return this.http.put<IRelativeMaster>(`${this.resourceUrl}/${this.getRelativeMasterIdentifier(relativeMaster)}`, relativeMaster, {
      observe: 'response',
    });
  }

  partialUpdate(relativeMaster: PartialUpdateRelativeMaster): Observable<EntityResponseType> {
    return this.http.patch<IRelativeMaster>(`${this.resourceUrl}/${this.getRelativeMasterIdentifier(relativeMaster)}`, relativeMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRelativeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelativeMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRelativeMasterIdentifier(relativeMaster: Pick<IRelativeMaster, 'id'>): number {
    return relativeMaster.id;
  }

  compareRelativeMaster(o1: Pick<IRelativeMaster, 'id'> | null, o2: Pick<IRelativeMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getRelativeMasterIdentifier(o1) === this.getRelativeMasterIdentifier(o2) : o1 === o2;
  }

  addRelativeMasterToCollectionIfMissing<Type extends Pick<IRelativeMaster, 'id'>>(
    relativeMasterCollection: Type[],
    ...relativeMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const relativeMasters: Type[] = relativeMastersToCheck.filter(isPresent);
    if (relativeMasters.length > 0) {
      const relativeMasterCollectionIdentifiers = relativeMasterCollection.map(
        relativeMasterItem => this.getRelativeMasterIdentifier(relativeMasterItem)!
      );
      const relativeMastersToAdd = relativeMasters.filter(relativeMasterItem => {
        const relativeMasterIdentifier = this.getRelativeMasterIdentifier(relativeMasterItem);
        if (relativeMasterCollectionIdentifiers.includes(relativeMasterIdentifier)) {
          return false;
        }
        relativeMasterCollectionIdentifiers.push(relativeMasterIdentifier);
        return true;
      });
      return [...relativeMastersToAdd, ...relativeMasterCollection];
    }
    return relativeMasterCollection;
  }
}
