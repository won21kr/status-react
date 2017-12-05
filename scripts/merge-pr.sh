#!/usr/bin/env sh

set -eof pipefail

BRANCH=$1
if [[ $# -eq 0 ]] ; then
    echo 'Branch required as first argument'
    exit 0
fi

# Checkout branch
git remote update origin
git checkout -B $BRANCH origin/$BRANCH && git pull

# Rebase and squash to one commit
git rebase -i origin/develop

# Verify signature and commit, then force push to PR
git show --show-signature
git push -f

# Checkout develop and merge with same SHA
git checkout develop && git pull
git merge --ff-only $BRANCH

# Push to protected develop branch
git push
